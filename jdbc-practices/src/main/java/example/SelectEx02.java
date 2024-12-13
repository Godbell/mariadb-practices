package example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SelectEx02
{
    public static void main(String[] args) {
        List<DepartmentVO> list = search("팀");

        for (final DepartmentVO vo : list) {
            System.out.println(vo);
        }
    }
    public static List<DepartmentVO> search(String keyword)
    {
        List<DepartmentVO> results = new ArrayList<>();
        boolean isSuccessful = false;
        Connection connection = null;

        try {
            Class.forName("org.mariadb.jdbc.Driver");
            String url = "jdbc:mariadb://192.168.64.30:3306/webdb";
            connection = DriverManager.getConnection(
                url, "webdb", "webdb"
            );

            String sql = "SELECT id, name FROM department WHERE name LIKE ?";
            PreparedStatement query = connection.prepareStatement(sql);
            query.setString(1, "%" + keyword + "%");

            ResultSet result = query.executeQuery();
            if (result == null) {
                return null;
            }

            isSuccessful = true;

            while (result.next()) {
                long id = result.getLong(1);
                String name = result.getString(2);
                DepartmentVO vo = new DepartmentVO(id, name);
                results.add(vo);
            }
        } catch (ClassNotFoundException e) {
            System.out.println("failed to load driver: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("failed to connect db: " + e.getMessage());
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.out.println("failed to close db connection: " + e.getMessage());
                }
            }
        }

        return results;
    }
}
