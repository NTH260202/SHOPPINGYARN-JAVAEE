package com.thanhha.orderDetail;

import com.thanhha.util.DBHelper;

import javax.naming.NamingException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

import static com.sun.activation.registries.LogSupport.log;
import static com.thanhha.constant.ErrorMessage.PRODUCT.IS_QUANTITY_ERROR;
import static com.thanhha.constant.ErrorMessage.PRODUCT.QUANTITY_IS_INVALID;

public class OrderDetailDAO implements Serializable {
    private Connection connection;
    private PreparedStatement statement;
    private ResultSet resultSet;
    public void closeConnection() throws SQLException {
        if (this.resultSet != null) {
            resultSet.close();
        }

        if (statement != null) {
            statement.close();
        }

        if (connection != null) {
            connection.close();
        }
    }

    public boolean createOrderDetail(HashMap<String, Integer> items, BigDecimal total, String customerId) throws SQLException, NamingException {
        try {
            String id = "";
            int rows = 0;
            connection = DBHelper.makeConnection();
            if (connection != null) {
                connection.setAutoCommit(false);
                String orderSql = "INSERT [order](total) OUTPUT INSERTED.ID VALUES(?)";
                statement = connection.prepareStatement(orderSql);
                statement.setBigDecimal(1, total);
                resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    id = resultSet.getString("ID");
                }
                for (Map.Entry<String, Integer> item : items.entrySet()) {
                    String oderDetailSql = "INSERT [order_detail](orderId, productId, quantity, customerId) " +
                            "VALUES (?, ?, ?, ?)";
                    statement = connection.prepareStatement(oderDetailSql);
                    statement.setString(1, id);
                    statement.setString(2, item.getKey());
                    statement.setInt(3, item.getValue());
                    statement.setString(4, customerId);
                    rows = statement.executeUpdate();
                }
                if (rows > 0) {
                    connection.commit();
                    return true;
                }
            }
        } catch (SQLException e) {
            log("OrderDetailDAO: " + e.getMessage());
            System.out.println(e.getMessage());
            e.printStackTrace();
            System.out.println("We have roll back");
            if (e.getMessage().contains("CHK_in_stock")) {
                IS_QUANTITY_ERROR = true;
            }
            if (connection != null) {
                connection.rollback();
            }
        } finally {
            closeConnection();
        }
        return false;
    }
}
