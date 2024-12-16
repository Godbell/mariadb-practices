package bookmall.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

import bookmall.DataSource;
import bookmall.vo.UserVo;
public class UserDao extends Dao {
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
            logQueryFailure(e);
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
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logQueryFailure(e);
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
            logQueryFailure(e);
        }

        return result;
    }
}
