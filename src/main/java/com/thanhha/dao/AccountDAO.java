package com.thanhha.dao;

import com.thanhha.dto.AccountDTO;
import com.thanhha.util.DBHelper;

import javax.naming.NamingException;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AccountDAO implements Serializable {
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
    public List<AccountDTO> getAccountByFirstname(String searchValue) throws SQLException, NamingException {
        List<AccountDTO> accountList = null;

        try {
            connection = DBHelper.makeConnection();
            if (connection != null) {
                String sql = "SELECT username, [password], isAdmin, firstname " +
                        " FROM account " +
                        " WHERE firstname like ?";
                statement = connection.prepareStatement(sql);
                statement.setString(1,"%" + searchValue + "%");
                resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    String username = resultSet.getString("username");
                    String password = resultSet.getString("password");
                    String firstname = resultSet.getString("firstname");
                    boolean isAdmin = resultSet.getBoolean("isAdmin");

                    AccountDTO account = new AccountDTO(username, password, firstname, isAdmin, null);
                    if (accountList == null) {
                        accountList = new ArrayList<>();
                    }
                    accountList.add(account);
                }
            }
        } finally {
            closeConnection();
        }
        return accountList;
    }

    public boolean deleteAccountByUsername(String username) throws SQLException, NamingException {
        try {
            connection = DBHelper.makeConnection();
            if (connection != null) {
                String sql = "DELETE " +
                        " FROM account " +
                        " WHERE username = ?";
                statement = connection.prepareStatement(sql);
                statement.setString(1,username);
                int rows = statement.executeUpdate();

                if (rows > 0) {
                    return true;
                }
            }
        } finally {
            closeConnection();
        }
        return false;
    }

    public boolean updateAccountByUsername(String updatePK, String password, boolean isAdmin)
            throws SQLException, NamingException {
        try {
            connection = DBHelper.makeConnection();
            if (connection != null) {
                String sql = "UPDATE account" +
                        " SET password = ?, isAdmin = ? " +
                        " WHERE username = ?";
                statement = connection.prepareStatement(sql);
                statement.setString(1, password);
                statement.setBoolean(2, isAdmin);
                statement.setString(3, updatePK);
                int rows = statement.executeUpdate();

                if (rows > 0) {
                    return true;
                }
            }
        } finally {
            closeConnection();
        }
        return false;
    }
}
