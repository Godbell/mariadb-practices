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
import bookmall.vo.CategoryVo;
public class CategoryDao {
    public void insert(CategoryVo vo) {
        try (
            Connection connection = DataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                """
                    INSERT INTO category (name) VALUES (?);
                    """,
                Statement.RETURN_GENERATED_KEYS
            );
        ) {
            preparedStatement.setString(1, vo.getName());

            preparedStatement.executeUpdate();
            ResultSet keys = preparedStatement.getGeneratedKeys();

            keys.next();
            vo.setNo(keys.getInt(1));
        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void deleteByNo(int no) {
        if (no < 0) {
            System.out.println("Invalid Category No..");
            return;
        }

        try (
            Connection connection = DataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                """
                    DELETE FROM category WHERE no = ?;
                    """
            );
        ) {
            preparedStatement.setInt(1, no);
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

    public Collection<CategoryVo> findAll() {
        Collection<CategoryVo> result = new ArrayList<>();

        try (
            Connection connection = DataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                """
                    SELECT no, name FROM category;
                    """,
                Statement.RETURN_GENERATED_KEYS
            );
        ) {
            ResultSet resulSet = preparedStatement.executeQuery();

            while (resulSet.next()) {
                int no = resulSet.getInt("no");
                String name = resulSet.getString("name");

                result.add(
                    new CategoryVo(no, name)
                );
            }
        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            e.printStackTrace();
        }

        return result;
    }
}
