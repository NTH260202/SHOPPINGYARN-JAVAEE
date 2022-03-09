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
        String userAccess;
        ServletContext context = request.getServletContext();
        Properties userAccessMap = (Properties) context.getAttribute("USER_ACCESS");
        String servletPath = servletRequest.getServletPath();
        String resource = servletPath.substring(1);
        userAccess = userAccessMap.getProperty(resource);
        if (session == null && userAccess.equals("restricted")) {
            RequestDispatcher dispatcher = servletRequest.getRequestDispatcher(LOGIN_PAGE);
            dispatcher.forward(request, response);
        } else {
            chain.doFilter(request, response);
        }
    }
}
