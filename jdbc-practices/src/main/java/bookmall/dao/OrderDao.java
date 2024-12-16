package bookmall.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import bookmall.DataSource;
import bookmall.vo.OrderBookVo;
import bookmall.vo.OrderVo;
public class OrderDao extends Dao {
    public void insert(OrderVo vo) {
        try (
            Connection connection = DataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                """
                    INSERT INTO orders (number, state, dst, price, user_no) VALUES (?, ?, ?, ?, ?);
                    """,
                Statement.RETURN_GENERATED_KEYS
            );
        ) {
            preparedStatement.setString(1, vo.getNumber());
            preparedStatement.setString(2, vo.getStatus());
            preparedStatement.setString(3, vo.getShipping());
            preparedStatement.setInt(4, vo.getPayment());
            preparedStatement.setLong(5, vo.getUserNo());

            preparedStatement.executeUpdate();

            ResultSet keys = preparedStatement.getGeneratedKeys();
            keys.next();
            vo.setNo(keys.getLong(1));
        } catch (SQLException e) {
            logQueryFailure(e);
        }
    }

    public void insertBook(OrderBookVo vo) {
        try (
            Connection connection = DataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                """
                    INSERT INTO orders_book
                        (quantity, order_no, book_no, price)
                    VALUES
                        (?, ?, ?, ?);
                    """
            );
        ) {
            preparedStatement.setInt(1, vo.getQuantity());
            preparedStatement.setLong(2, vo.getOrderNo());
            preparedStatement.setLong(3, vo.getBookNo());
            preparedStatement.setInt(4, vo.getPrice());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logQueryFailure(e);
        }
    }

    public void deleteBooksByNo(Long orderNo) {
        // remove order_books
        if (orderNo == null) {
            System.out.println("Invalid Order No..");
            return;
        }

        try (
            Connection connection = DataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                """
                    DELETE FROM orders_book WHERE order_no = ?;
                    """
            );
        ) {
            preparedStatement.setLong(1, orderNo);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logQueryFailure(e);
        }
    }

    public void deleteByNo(Long no) {
        try (
            Connection connection = DataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                """
                    DELETE FROM orders WHERE no = ?;
                    """
            );
        ) {
            preparedStatement.setLong(1, no);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logQueryFailure(e);
        }
    }

    public List<OrderBookVo> findBooksByNoAndUserNo(Long orderNo, Long userNo) {
        List<OrderBookVo> result = new ArrayList<>();

        try (
            Connection connection = DataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                """
                    SELECT
                        orders_book.no AS "no",
                        quantity, order_no, book_no, orders_book.price, title, state
                    FROM orders_book
                    LEFT JOIN orders
                        ON orders_book.order_no = orders.no
                    LEFT JOIN book
                        ON orders_book.book_no = book.no
                    WHERE order_no = ?
                        AND user_no = ?
                    """
            );
        ) {
            preparedStatement.setLong(1, orderNo);
            preparedStatement.setLong(2, userNo);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                OrderBookVo vo = new OrderBookVo();
                vo.setNo(resultSet.getLong("no"));
                vo.setBookNo(resultSet.getLong("book_no"));
                vo.setOrderNo(resultSet.getLong("order_no"));
                vo.setBookTitle(resultSet.getString("title"));
                vo.setQuantity(resultSet.getInt("quantity"));
                vo.setPrice(resultSet.getInt("price"));

                result.add(vo);
            }
        } catch (SQLException e) {
            logQueryFailure(e);
        }

        return result;
    }

    public OrderVo findByNoAndUserNo(Long orderNo, Long userNo) {
        OrderVo orderVo = null;

        try (
            Connection connection = DataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                """
                    SELECT
                        orders.no AS "no",
                        number,
                        title,
                        orders.price AS "price",
                        user_no,
                        dst,
                        state
                    FROM orders
                    LEFT JOIN orders_book ON orders.no = orders_book.order_no
                    LEFT JOIN book ON orders_book.book_no = book.no
                    WHERE orders.no = ? AND user_no = ?
                    """
            );
        ) {
            preparedStatement.setLong(1, orderNo);
            preparedStatement.setLong(2, userNo);

            ResultSet resultSet = preparedStatement.executeQuery();

            boolean hasNext = resultSet.next();
            if (hasNext) {
                orderVo = new OrderVo();

                orderVo.setNo(resultSet.getLong("no"));
                orderVo.setUserNo(resultSet.getLong("user_no"));
                orderVo.setPayment(resultSet.getInt("price"));
                orderVo.setStatus(resultSet.getString("state"));
                orderVo.setTitle(resultSet.getString("title"));
                orderVo.setNumber(resultSet.getString("number"));
                orderVo.setShipping(resultSet.getString("dst"));
            }
        } catch (SQLException e) {
            logQueryFailure(e);
        }

        return orderVo;
    }
}
