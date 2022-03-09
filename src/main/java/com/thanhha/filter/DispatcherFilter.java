package com.thanhha.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Properties;

import static com.sun.activation.registries.LogSupport.log;

public class DispatcherFilter implements Filter {
    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws ServletException, IOException {
//        HttpServletRequest servletRequest = (HttpServletRequest) request;
//        String url = SEARCH_PAGE;
//        String uri = servletRequest.getRequestURI();
//        ServletContext context = servletRequest.getServletContext();
//        try {
//            int lastIndex = uri.lastIndexOf("/");
//            String resource = uri.substring(lastIndex + 1);
//            HashMap<String, String> resourceMap = (HashMap<String, String>) context.getAttribute("PATH_MAP");
//
//            if (resource.length() > 0) {
//                url = resourceMap.get(resource);
//            }
//            if (url != null) {
//                RequestDispatcher dispatcher = request.getRequestDispatcher(url);
//                dispatcher.forward(request,response);
//            } else {
//                chain.doFilter(request, response);
//            }
//        } catch (Throwable t) {
//            log(t.getMessage());
//        }
        HttpServletRequest servletRequest = (HttpServletRequest) request;
        String url;
        try {
            //get site map
            ServletContext context = request.getServletContext();
            Properties siteMap =
                    (Properties) context.getAttribute("SITE_MAP");
            //get resource name
//            int lastIndex = uri.lastIndexOf("/");
//            String resource = uri.substring(lastIndex + 1);
//            //get site mapping
//            url = siteMap.getProperty(resource);
            String servletPath = servletRequest.getServletPath();
            String resource = servletPath.substring(1);
            url = siteMap.getProperty(resource);
            System.out.println("ServletPath " + servletPath);
            if (url != null) {
                RequestDispatcher dispatcher = servletRequest.getRequestDispatcher(url);
                dispatcher.forward(request, response);
            }
        } catch (Throwable t) {
            log(t.getMessage());
        }

    }
}
