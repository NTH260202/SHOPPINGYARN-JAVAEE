package com.thanhha.filter;

import com.thanhha.dto.AccountDTO;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Properties;

import static com.thanhha.constant.ResourceUrl.PathValue.LOGIN_PAGE;

public class AuthenticateFilter implements Filter {
    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest servletRequest = (HttpServletRequest) request;

        HttpSession session = servletRequest.getSession(false);
        System.out.println("Session" + session);
        HttpServletRequest req = (HttpServletRequest) request;
        String uri = req.getRequestURI();
        String userAccess;
        ServletContext context = request.getServletContext();
        Properties userAccessMap = (Properties) context.getAttribute("USER_ACCESS");
        int lastIndex = uri.lastIndexOf("/");
        String resource = uri.substring(lastIndex + 1);
        System.out.println(resource);
        userAccess = userAccessMap.getProperty(resource);
        if (session == null && userAccess.equals("restricted")) {
            RequestDispatcher dispatcher = servletRequest.getRequestDispatcher(LOGIN_PAGE);
            dispatcher.forward(request, response);
        } else {
            System.out.println("Im passing filter");
            chain.doFilter(request, response);
        }
    }
}
