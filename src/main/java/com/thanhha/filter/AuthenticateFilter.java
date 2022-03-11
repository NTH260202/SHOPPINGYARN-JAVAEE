package com.thanhha.filter;

import com.thanhha.account.AccountDTO;
import com.thanhha.constant.ResourceUrl;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Properties;

import static com.thanhha.constant.ResourceUrl.PathName.LOGIN_PAGE;
import static com.thanhha.constant.ResourceUrl.PathName.SEARCH_PAGE;

public class AuthenticateFilter implements Filter {
    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {

        HttpServletRequest servletRequest = (HttpServletRequest) request;
        HttpServletResponse servletResponse = (HttpServletResponse) response;

        servletResponse.setHeader("Cache-Control", "no-cache, no-store");
        servletRequest.setCharacterEncoding("UTF-8");
        servletResponse.setContentType("text/html;charset=UTF-8");

        HttpSession session = servletRequest.getSession(false);
        ServletContext context = request.getServletContext();

        Properties userAccessMap = (Properties) context.getAttribute("USER_ACCESS");

        String servletPath = servletRequest.getServletPath();
        String resource = servletPath.substring(1);
        String userAccess = userAccessMap.getProperty(resource);

//        if (session == null && userAccess.equals("restricted")) {
//            System.out.println(session);
//            System.out.println("Im in loginPage");
//            servletResponse.sendRedirect(LOGIN_PAGE);
//        } else {
//            if (session != null && userAccess.equals("user_restricted")) {
//                servletResponse.sendRedirect(SEARCH_PAGE);
//            } else {
//                chain.doFilter(request, response);
//            }
//        }
        if (session != null) {
            AccountDTO validUser = (AccountDTO) session.getAttribute("USER");
            if (validUser != null) {
//                if (userAccess.equals("user_restricted")) {
////                    RequestDispatcher dispatcher = servletRequest.getRequestDispatcher(ResourceUrl.PathValue.LOGIN_PAGE);
////                    dispatcher.forward(request, response);
//                    servletResponse.sendRedirect(SEARCH_PAGE);
//                } else {
                    chain.doFilter(request, response);
//                }
            } else {
                if (userAccess.equals("restricted")) {
//                    RequestDispatcher dispatcher = servletRequest.getRequestDispatcher(ResourceUrl.PathValue.LOGIN_PAGE);
//                    dispatcher.forward(request, response);
                    servletResponse.sendRedirect(LOGIN_PAGE);
                } else {
                    chain.doFilter(request, response);
                }
            }
        } else {
            if (userAccess.equals("restricted")) {
//                RequestDispatcher dispatcher = servletRequest.getRequestDispatcher(ResourceUrl.PathValue.LOGIN_PAGE);
//                dispatcher.forward(request, response);
                servletResponse.sendRedirect(LOGIN_PAGE);
            } else {
                chain.doFilter(request, response);
            }
        }

    }
}
