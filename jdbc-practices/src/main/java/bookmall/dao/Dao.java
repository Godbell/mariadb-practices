package bookmall.dao;

import java.sql.SQLException;
public abstract class Dao {
    protected void logResetTransaction(Exception e) {
        System.out.println("Rollbacked transaction due to: " + e.getMessage());
        e.printStackTrace();
    }

    protected void logQueryFailure(SQLException e) {
        System.out.println("Query failed: " + e.getMessage());
        e.printStackTrace();
    }
}
