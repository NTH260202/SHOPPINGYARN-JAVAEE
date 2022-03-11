package com.thanhha.filter;

import com.thanhha.account.AccountDTO;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Properties;

import static com.thanhha.constant.ResourceUrl.PathName.PRODUCT_PAGE;
import static com.thanhha.constant.ResourceUrl.PathName.SEARCH_PAGE;

public class AuthorizeFilter implements Filter {
    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {

        HttpServletRequest servletRequest = (HttpServletRequest) request;
        HttpServletResponse servletResponse = (HttpServletResponse) response;

        HttpSession session = servletRequest.getSession(false);
        ServletContext context = request.getServletContext();

        Properties userAccessMap = (Properties) context.getAttribute("USER_ROLE");

        String servletPath = servletRequest.getServletPath();
        String resource = servletPath.substring(1);
        String role = userAccessMap.getProperty(resource);

        String userRole = "guest";

        System.out.println(session);

        if (session != null) {
            AccountDTO validUser = (AccountDTO) session.getAttribute("USER");
            if (validUser != null) {
                System.out.println(validUser.getUsername());
                System.out.println(validUser.isAdmin());
                if (validUser.isAdmin()) {
                    userRole = "admin";
                } else {
                    userRole = "user";
                }
            }
        }

        if ((resource.equals("") || resource.equals("login")) && userRole.equals("admin")) {
            servletResponse.sendRedirect(SEARCH_PAGE);
        }
        if ((resource.equals("") || resource.equals("login")) && userRole.equals("user")) {
            servletResponse.sendRedirect(PRODUCT_PAGE);
        }
        System.out.println(role + " " + userRole);
        if (role.contains(userRole)) {
            chain.doFilter(request, response);
        } else {
            servletResponse.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}
