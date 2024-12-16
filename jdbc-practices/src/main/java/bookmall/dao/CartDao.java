package bookmall.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import bookmall.DataSource;
import bookmall.vo.CartVo;
public class CartDao {
    public void insert(CartVo vo) {
        try (
            Connection connection = DataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                """
                    INSERT INTO cart (user_no, book_no, quantity) VALUES (?, ?, ?);
                    """,
                Statement.RETURN_GENERATED_KEYS
            );
        ) {
            preparedStatement.setLong(1, vo.getUserNo());
            preparedStatement.setLong(2, vo.getBookNo());
            preparedStatement.setInt(3, vo.getQuantity());
            preparedStatement.executeUpdate();

            ResultSet keys = preparedStatement.getGeneratedKeys();
            keys.next();
            
            vo.setNo(keys.getLong(1));
        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public List<CartVo> findByUserNo(Long userNo) {
        List<CartVo> result = new ArrayList<>();

        try (
            Connection connection = DataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                """
                    SELECT cart.no, quantity, user_no, book_no, title
                    FROM cart
                    LEFT JOIN book ON cart.book_no = book.no
                    WHERE user_no = ?
                    """,
                Statement.RETURN_GENERATED_KEYS
            );
        ) {
            preparedStatement.setLong(1, userNo);

            ResultSet keys = preparedStatement.executeQuery();
            while (keys.next()) {
                CartVo vo = new CartVo();
                vo.setNo(keys.getLong("no"));
                vo.setQuantity(keys.getInt("quantity"));
                vo.setUserNo(keys.getLong("user_no"));
                vo.setBookNo(keys.getLong("book_no"));
                vo.setBookTitle(keys.getString("title"));

                result.add(vo);
            }
        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            e.printStackTrace();
        }

        return result;
    }

    public void deleteByUserNoAndBookNo(Long userNo, Long bookNo) {
        try (
            Connection connection = DataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                """
                    DELETE FROM cart WHERE user_no = ? AND book_no = ?;
                    """
            );
        ) {
            preparedStatement.setLong(1, userNo);
            preparedStatement.setLong(2, bookNo);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
