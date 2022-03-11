package com.thanhha.controller.account;

import com.thanhha.account.AccountDAO;

import javax.naming.NamingException;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;

import static com.thanhha.constant.ResourceUrl.PathName.ERROR_PAGE;

@WebServlet(name = "DeleteAccountServlet", value = "/DeleteAccountServlet")
public class DeleteAccountServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = ERROR_PAGE;

        String lastSearchValue = request.getParameter("lastSearchValue");
        String username = request.getParameter("deleteValue");
        try {
            AccountDAO dao = new AccountDAO();
            boolean result = dao.deleteAccountByUsername(username);
            if (result) {
                url = "searchAccount?txtSearchValue=" + lastSearchValue;
            }

        } catch (SQLException e) {
            log("AuthenticateServlet _SQLException: " + e.getMessage());
        } catch (NamingException e) {
            log("AuthenticateServlet _NamingException: " + e.getMessage());
        } finally {
            response.sendRedirect(url);
        }
    }
}
