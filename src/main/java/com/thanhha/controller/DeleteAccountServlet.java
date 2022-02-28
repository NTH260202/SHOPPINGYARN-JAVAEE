package com.thanhha.controller;

import com.thanhha.dao.AccountDAO;

import javax.naming.NamingException;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;

import static com.thanhha.constant.ResourceUrl.ERROR_PAGE;
import static com.thanhha.constant.ResourceUrl.SEARCH_PAGE_RESULT;

@WebServlet(name = "DeleteAccountServlet", value = "/DeleteAccountServlet")
public class DeleteAccountServlet extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException, NamingException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR_PAGE;

        String lastSearchValue = request.getParameter("lastSearchValue");
        String username = request.getParameter("deleteValue");

        System.out.println(username);
        System.out.println(lastSearchValue);
        AccountDAO dao = new AccountDAO();
        boolean result = dao.deleteAccountByUsername(username);
        if (result) {
            url = "search?txtSearchValue=" + lastSearchValue;
        }
        response.sendRedirect(url);
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            processRequest(request,response);
        } catch (SQLException | NamingException e) {
            log(e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
