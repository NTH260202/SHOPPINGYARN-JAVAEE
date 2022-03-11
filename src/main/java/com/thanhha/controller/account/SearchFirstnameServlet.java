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
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            HttpSession session = request.getSession(false);
            AccountDTO currentUser = (AccountDTO) session.getAttribute("USER");

            String searchValue = request.getParameter("txtSearchValue");
            String url = SEARCH_PAGE_RESULT;

            AccountDAO accountDAO = new AccountDAO();
            List<AccountDTO> accountList = accountDAO.getAccountByFirstname(searchValue);

            if (accountList != null) {
                for (AccountDTO account : accountList) {
                    if (currentUser.getUsername().equals(account.getUsername())) {
                        accountList.remove(account);
                        break;
                    }
                }
            }

            request.setAttribute("SEARCH_RESULT", accountList);

            RequestDispatcher dispatcher = request.getRequestDispatcher(url);
            dispatcher.forward(request,response);
        } catch (SQLException e) {
            log("SearchFirstnameServlet_SQLException: " + e.getMessage());
        } catch (NamingException e) {
            log("SearchFirstnameServlet_NamingException: " + e.getMessage());
        }
    }
}
