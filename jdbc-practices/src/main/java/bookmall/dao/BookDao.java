package bookmall.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import bookmall.DataSource;
import bookmall.vo.BookVo;
public class BookDao extends Dao {
    public void insert(BookVo vo) {
        try (
            Connection connection = DataSource.getConnection();
            PreparedStatement bookInsertStatement = connection.prepareStatement(
                """
                    INSERT INTO book (title, price) VALUES (?, ?);
                    """,
                Statement.RETURN_GENERATED_KEYS
            );
            PreparedStatement bookCategoryInsertStatement = connection.prepareStatement(
                """
                    INSERT INTO book_category (category_no, book_no) VALUES (?, ?);
                    """,
                Statement.RETURN_GENERATED_KEYS
            );
        ) {
            connection.setAutoCommit(false);

            try {
                bookInsertStatement.setString(1, vo.getTitle());
                bookInsertStatement.setInt(2, vo.getPrice());
                bookInsertStatement.executeUpdate();

                ResultSet keys = bookInsertStatement.getGeneratedKeys();
                keys.next();
                vo.setNo(keys.getLong(1));

                bookCategoryInsertStatement.setInt(1, vo.getCategoryNo());
                bookCategoryInsertStatement.setLong(2, vo.getNo());
                bookCategoryInsertStatement.executeUpdate();

                connection.commit();
            } catch (Exception e) {
                connection.rollback();
                logResetTransaction(e);
            }
        } catch (SQLException e) {
            logQueryFailure(e);
        }
    }

    public void deleteByNo(Long no) {
        if (no == null) {
            System.out.println("Invalid Book No..");
            return;
        }

        try (
            Connection connection = DataSource.getConnection();
            PreparedStatement bookCategoryDeleteStatement = connection.prepareStatement(
                """
                    DELETE FROM book_category WHERE book_no = ?;
                    """
            );
            PreparedStatement bookDeleteStatement = connection.prepareStatement(
                """
                    DELETE FROM book WHERE no = ?;
                    """
            );
        ) {
            connection.setAutoCommit(false);
            try {
                bookCategoryDeleteStatement.setLong(1, no);
                bookCategoryDeleteStatement.executeUpdate();

                bookDeleteStatement.setLong(1, no);
                bookDeleteStatement.executeUpdate();

                connection.commit();
            } catch (Exception e) {
                connection.rollback();
                logResetTransaction(e);
            }
        } catch (SQLException e) {
            logQueryFailure(e);
        }
    }
}
