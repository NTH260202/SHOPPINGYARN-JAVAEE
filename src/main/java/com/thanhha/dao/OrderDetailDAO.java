package com.thanhha.dao;

import com.thanhha.dto.OrderDetailDTO;
import com.thanhha.util.DBHelper;

import javax.naming.NamingException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.sun.activation.registries.LogSupport.log;

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

    public boolean createOrderDetail(HashMap<String, Integer> items, BigDecimal total, String customerId) throws SQLException {
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
                    System.out.println("ROWS" + rows);
                    connection.commit();
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            String errorMessage = e.getMessage();
            log("OrderDetailDAO " + errorMessage);
            if (connection != null) {
                connection.rollback();
            }
        } finally {
            closeConnection();
        }
        return false;
    }
}
