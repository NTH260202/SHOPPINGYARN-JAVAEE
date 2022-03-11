package com.thanhha.controller.account;

import com.thanhha.account.AccountDAO;
import com.thanhha.account.AccountDTO;

import javax.naming.NamingException;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static com.thanhha.constant.ResourceUrl.PathValue.SEARCH_PAGE_RESULT;

@WebServlet(name = "SearchFirstnameServlet", value = "/SearchFirstnameServlet")
public class SearchFirstnameServlet extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, SQLException, NamingException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        String searchValue = request.getParameter("txtSearchValue");
        String url = SEARCH_PAGE_RESULT;

        AccountDAO accountDAO = new AccountDAO();
        List<AccountDTO> accountList = accountDAO.getAccountByFirstname(searchValue);
        request.setAttribute("SEARCH_RESULT", accountList);

        RequestDispatcher dispatcher = request.getRequestDispatcher(url);
        dispatcher.forward(request,response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException | NamingException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
