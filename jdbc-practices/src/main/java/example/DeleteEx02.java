package example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteEx02
{
    public static void main(String[] args) {
        delete(1L);
    }
    public static boolean delete(long id)
    {
        boolean isSuccessful = false;
        Connection connection = null;

        try {
            Class.forName("org.mariadb.jdbc.Driver");
            String url = "jdbc:mariadb://192.168.64.30:3306/webdb";
            connection = DriverManager.getConnection(
                url, "webdb", "webdb"
            );

            String sql = "DELETE FROM department WHERE id=?";
            PreparedStatement query = connection.prepareStatement(sql);
            query.setLong(1, id);

            int count = query.executeUpdate();
            isSuccessful = count == 1;
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

        return isSuccessful;
    }
}
