package com.thanhha.controller.account;

import com.thanhha.account.AccountDAO;
import com.thanhha.account.AccountDTO;

import javax.naming.NamingException;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import static com.thanhha.constant.ResourceUrl.PathName.*;
import static com.thanhha.util.ParsingUtils.hashString;

@WebServlet(name = "AuthenticateServlet", value = "/AuthenticateServlet")
public class AuthenticateServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();

        String url = INVALID_ACCOUNT_PAGE;
        //get request parameters
        String username = request.getParameter("txtUsername");
        String password = request.getParameter("txtPassword");

        try {
            String hashedPassword = hashString(password);

            AccountDAO dao = new AccountDAO();
            AccountDTO validUser =
                    dao.getAccountByUsernameAndPassword(
                            username, hashedPassword);
            if (validUser != null) {
                if (validUser.isAdmin()) {
                    url = SEARCH_PAGE;
                } else {
                    url = PRODUCT_PAGE;
                };
                //create new session
                HttpSession session = request.getSession(true);
                session.setAttribute("USER", validUser);
            }//end if validAccount is not null
        } catch (SQLException e) {
            System.out.println("Im here after login sql " + e.getMessage());
            log("AuthenticateServlet_SQLException: " + e.getMessage());
        } catch (NamingException e) {
            System.out.println("Im here after login naming");
            log("AuthenticateServlet_NamingException: " + e.getMessage());
        } catch (NoSuchAlgorithmException e) {
            log("AuthenticateServlet_NoSuchAlgorithmException: " + e.getMessage());
        } finally {
            response.sendRedirect(url);
            out.close();
        }
    }
}
