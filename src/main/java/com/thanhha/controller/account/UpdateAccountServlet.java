package com.thanhha.controller.account;

import com.thanhha.dao.AccountDAO;

import javax.naming.NamingException;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;

import static com.thanhha.constant.ResourceUrl.PathName.ERROR_PAGE;

@WebServlet(name = "UpdateAccountServlet", value = "/UpdateAccountServlet")
public class UpdateAccountServlet extends HttpServlet {

    protected  void processHandle(HttpServletRequest request, HttpServletResponse response)
            throws IOException, SQLException, NamingException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR_PAGE;

        String lastSearchValue = request.getParameter("updateParam");
        String updateKey = request.getParameter("updatePK");
        String password = request.getParameter("password");
        boolean isAdmin = Boolean.parseBoolean(request.getParameter("isAdmin"));

        AccountDAO accountDAO = new AccountDAO();
        boolean result = accountDAO.updateAccountByUsername(updateKey, password, isAdmin);
        if (result) {
            url = "searchAccount?txtSearchValue=" + lastSearchValue;
        }

        response.sendRedirect(url);
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            processHandle(request, response);
        } catch (SQLException | IOException | NamingException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
