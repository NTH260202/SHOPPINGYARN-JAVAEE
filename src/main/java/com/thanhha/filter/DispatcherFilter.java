package com.thanhha.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Properties;

import static com.sun.activation.registries.LogSupport.log;
import static com.thanhha.constant.ResourceUrl.PathName.LOGIN_PAGE;
import static com.thanhha.constant.ResourceUrl.PathValue.SEARCH_PAGE_RESULT;

public class DispatcherFilter implements Filter {
    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        HttpServletRequest servletRequest = (HttpServletRequest) request;
        String url;
        try {
            ServletContext context = request.getServletContext();
            Properties siteMap = (Properties) context.getAttribute("SITE_MAP");
            String servletPath = servletRequest.getServletPath();
            String resource = servletPath.substring(1);
            System.out.println("resource " + resource);
//            if (resource.equals("")) {
//                url = SEARCH_PAGE_RESULT;
//            } else {
                url = siteMap.getProperty(resource);
//            }
            if (url != null) {
                RequestDispatcher dispatcher = servletRequest.getRequestDispatcher(url);
                dispatcher.forward(request, response);
            }
        } catch (Throwable t) {
            log(t.getMessage());
        }

    }
}
