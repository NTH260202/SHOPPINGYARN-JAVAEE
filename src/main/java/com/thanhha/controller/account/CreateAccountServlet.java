package com.thanhha.controller.account;

import com.thanhha.account.AccountDAO;
import com.thanhha.account.AccountDTO;
import com.thanhha.account.AccountRegisterError;

import javax.naming.NamingException;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;

import static com.thanhha.constant.ErrorMessage.ACCOUNT.*;
import static com.thanhha.constant.ResourceUrl.PathValue.LOGIN_PAGE;
import static com.thanhha.constant.ResourceUrl.PathValue.REGISTER_ERROR_PAGE;

@WebServlet(name = "CreateAccountServlet", value = "/CreateAccountServlet")
public class CreateAccountServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        boolean foundError = false;
        AccountRegisterError error = new AccountRegisterError();
        String url = REGISTER_ERROR_PAGE;

        String username = request.getParameter("txtUsername");
        String password  = request.getParameter("txtPassword");
        String confirmedPassword = request.getParameter("txtConfirm");
        String firstname = request.getParameter("txtFirstname");
        String lastname = request.getParameter("txtLastname");

        if (username.trim().length() < 6 ||
                username.trim().length() > 40) {
            foundError = true;
            error.setUsernameLengthErr(USERNAME_LENGTH_ERROR);
        }
        if (password.trim().length() < 6 ||
                password.trim().length() > 30) {
            foundError = true;
            error.setPasswordLengthErr(PASSWORD_LENGTH_ERROR);
        } else if (!confirmedPassword.trim().equals(password.trim())){
            foundError = true;
            error.setConfirmPasswordNotMatched(CONFIRMED_PASSWORD_FAILED);
        }
        if (firstname.trim().length() < 2 ||
                firstname.trim().length() > 10) {
            foundError = true;
            error.setFirstnameLengthErr(FIRST_NAME_LENGTH_ERROR);
        }
        if (lastname.trim().length() < 6 ||
                lastname.trim().length() > 40) {
            foundError = true;
            error.setLastnameLengthErr(LAST_NAME_LENGTH_ERROR);
        }

        try {
            if(foundError) {
                request.setAttribute("ERROR_MESSAGE", error);
            } else {
                AccountDAO accountDAO = new AccountDAO();
                AccountDTO account = new AccountDTO(username, password, firstname, lastname);
                boolean result = accountDAO.createNewAccount(account);
                if (result) {
                    url = LOGIN_PAGE;
                }
            }
        } catch (SQLException e) {
            log("CreateAccountServlet_SQLException: " + e.getMessage());
            if (e.getMessage().contains("duplicate")) {
                error.setUsernameIsExisted(USERNAME_EXISTED);
                request.setAttribute("ERROR_MESSAGE", error);
            }
        } catch (NamingException e) {
            log("CreateAccountServlet_NamingException: " + e.getMessage());
        } finally {
        RequestDispatcher rd = request.getRequestDispatcher(url);
        rd.forward(request, response);
        }
    }
}
