package bookmall.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import bookmall.DataSource;
import bookmall.vo.UserVo;
public class UserDao {
    public void insert(UserVo vo) {
        try (
            Connection connection = DataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                """
                    INSERT INTO user
                        (name, email, password, tel)
                    VALUES (?, ?, ?, ?);
                    """,
                Statement.RETURN_GENERATED_KEYS
            );
        ) {
            preparedStatement.setString(1, vo.getName());
            preparedStatement.setString(2, vo.getEmail());
            preparedStatement.setString(3, vo.getPassword());
            preparedStatement.setString(4, vo.getTel());

            preparedStatement.executeUpdate();
            ResultSet keys = preparedStatement.getGeneratedKeys();

            keys.next();
            vo.setNo(keys.getLong(1));
        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void deleteByNo(Long no) {
        if (no < 0) {
            System.out.println("Invalid User No..");
            return;
        }

        try (
            Connection connection = DataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                """
                    DELETE FROM user WHERE no = ?;
                    """
            );
        ) {
            preparedStatement.setLong(1, no);
            int affectedRowsCount = preparedStatement.executeUpdate();

            if (affectedRowsCount != 1) {
                System.out.println("Query Anomally: pk based query affected more than one rows");
                System.out.println("Stack Trace: " + Arrays.toString(Thread.currentThread().getStackTrace()));
            }
        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public Collection<UserVo> findAll() {
        Collection<UserVo> result = new ArrayList<>();

        try (
            Connection connection = DataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                """
                    SELECT no, name, email, password, tel FROM user;
                    """,
                Statement.RETURN_GENERATED_KEYS
            );
        ) {
            ResultSet resulSet = preparedStatement.executeQuery();

            while (resulSet.next()) {
                Long no = resulSet.getLong("no");
                String name = resulSet.getString("name");
                String email = resulSet.getString("email");
                String password = resulSet.getString("password");
                String tel = resulSet.getString("tel");

                result.add(
                    new UserVo(no, name, email, password, tel)
                );
            }
        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            e.printStackTrace();
        }

        return result;
    }
}
