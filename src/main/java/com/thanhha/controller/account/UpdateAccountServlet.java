package com.thanhha.controller.account;

import com.thanhha.account.AccountDAO;

import javax.naming.NamingException;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import static com.thanhha.constant.ResourceUrl.PathName.ERROR_PAGE;
import static com.thanhha.util.ParsingUtils.hashString;

@WebServlet(name = "UpdateAccountServlet", value = "/UpdateAccountServlet")
public class UpdateAccountServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        try {
            String url = ERROR_PAGE;

            String lastSearchValue = request.getParameter("updateParam");
            String updateKey = request.getParameter("updatePK");
            String password = request.getParameter("password");
            boolean isAdmin = Boolean.parseBoolean(request.getParameter("isAdmin"));

            String hashedPassword = hashString(password);
            AccountDAO accountDAO = new AccountDAO();
            boolean result = accountDAO.updateAccountByUsername(updateKey, password, isAdmin);
            if (result) {
                url = "searchAccount?txtSearchValue=" + lastSearchValue;
            }
            response.sendRedirect(url);
        } catch (SQLException e) {
            log("UpdateAccountServlet_SQLException: " + e.getMessage());
        } catch (IOException e) {
            log("UpdateAccountServlet_IOException: " + e.getMessage());
        } catch (NamingException e) {
            log("UpdateAccountServlet_NamingException: " + e.getMessage());
        } catch (NoSuchAlgorithmException e) {
            log("UpdateAccountServlet_NoSuchAlgorithmException " + e.getMessage());
        }
    }
}
