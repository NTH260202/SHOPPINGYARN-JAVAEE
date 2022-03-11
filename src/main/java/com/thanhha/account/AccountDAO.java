package com.thanhha.account;

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

    public AccountDTO getAccountByUsernameAndPassword(String username, String password) throws SQLException, NamingException {
        AccountDTO account = null;
        try {
            //1. connect database
            connection = DBHelper.makeConnection();
            if (connection != null) {
                //2.Create SQL String
                String sql = "SELECT firstname, lastname, isAdmin " +
                        "FROM account " +
                        "WHERE username = ? AND password = ?";
                //3.Create statement
                statement = connection.prepareStatement(sql);
                statement.setString(1, username);
                statement.setString(2, password);
                //4.Query Data
                resultSet = statement.executeQuery();
                //5.Process Data
                if (resultSet.next()) {
                    String firstname = resultSet.getString("firstname");
                    String lastname = resultSet.getString("lastname");
                    boolean isAdmin = resultSet.getBoolean("isAdmin");
                    account = new AccountDTO(username, password, firstname, lastname, isAdmin);
                }
            }
        } finally {
            closeConnection();
        }
        return account;
    }
    public List<AccountDTO> getAccountByFirstname(String searchValue) throws SQLException, NamingException {
        List<AccountDTO> accountList = null;

        try {
            connection = DBHelper.makeConnection();
            if (connection != null) {
                String sql = "SELECT username, [password], isAdmin, firstname, lastname " +
                        " FROM account " +
                        " WHERE firstname like ?";
                statement = connection.prepareStatement(sql);
                statement.setString(1,"%" + searchValue + "%");
                resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    String username = resultSet.getString("username");
                    String password = resultSet.getString("password");
                    String firstname = resultSet.getString("firstname");
                    String lastname = resultSet.getString("lastname");
                    boolean isAdmin = resultSet.getBoolean("isAdmin");

                    AccountDTO account = new AccountDTO(username, password, firstname, isAdmin, null, lastname);
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

    public boolean createNewAccount(AccountDTO account) throws SQLException, NamingException {
        try {
            connection = DBHelper.makeConnection();
            if (connection != null) {
                String sql = "INSERT INTO account(username, password, firstname, lastname) " +
                        "VALUES(?, ?, ?, ?)";
                statement = connection.prepareStatement(sql);
                System.out.println(account.getUsername());
                statement.setString(1, account.getUsername());
                statement.setString(2, account.getPassword());
                statement.setString(3, account.getFirstname());
                statement.setString(4, account.getLastname());
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
