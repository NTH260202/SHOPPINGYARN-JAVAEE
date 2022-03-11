package com.thanhha.controller.account;

import com.thanhha.account.AccountDAO;
import com.thanhha.account.AccountDTO;

import javax.naming.NamingException;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import static com.thanhha.constant.ResourceUrl.PathName.INVALID_ACCOUNT_PAGE;
import static com.thanhha.constant.ResourceUrl.PathName.SEARCH_PAGE;

@WebServlet(name = "AuthenticateServlet", value = "/AuthenticateServlet")
public class AuthenticateServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        System.out.println("Im here after login");
        String url = INVALID_ACCOUNT_PAGE;
        //get request parameters
        String username = request.getParameter("txtUsername");
        String password = request.getParameter("txtPassword");
        try {
            AccountDAO dao = new AccountDAO();
            AccountDTO validUser =
                    dao.getAccountByUsernameAndPassword(
                            username, password);
            if (validUser != null) {
                url = SEARCH_PAGE;
                //create new session
                HttpSession session = request.getSession(true);
                session.setAttribute("USER", validUser);
            }//end if validAccount is not null
        } catch (SQLException ex) {
            log("AuthenticateServlet _SQLException: " + ex.getMessage());
        } catch (NamingException ex) {
            log("AuthenticateServlet _NamingException: " + ex.getMessage());
        } finally {
            response.sendRedirect(url);
            out.close();
        }
    }
}
