package com.thanhha.listener;

import com.thanhha.util.PropertiesFileHelper;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.util.Properties;

@WebListener
public class ContextListener implements ServletContextListener, HttpSessionListener, HttpSessionAttributeListener {

    public ContextListener() {
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();
        String siteMapLocation =
                context.getInitParameter("SITEMAP_PROPERTIES_FILE_LOCATION");
        String userAccess =
                context.getInitParameter("AUTHENTICATION_PROPERTIES_USER_ACCESS");
        String userRole =
                context.getInitParameter("AUTHORIZATION_PROPERTIES_USER_ROLE");

        Properties siteMapProperty = PropertiesFileHelper.getProperties(context, siteMapLocation);
        Properties accessRightProperty = PropertiesFileHelper.getProperties(context, userAccess);
        Properties userRoleProperty = PropertiesFileHelper.getProperties(context, userRole);

        context.setAttribute("SITE_MAP", siteMapProperty);
        context.setAttribute("USER_ACCESS", accessRightProperty);
        context.setAttribute("USER_ROLE", userRoleProperty);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        /* This method is called when the servlet Context is undeployed or Application Server shuts down. */
    }

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        /* Session is created. */
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        /* Session is destroyed. */
    }

    @Override
    public void attributeAdded(HttpSessionBindingEvent sbe) {
        /* This method is called when an attribute is added to a session. */
    }

    @Override
    public void attributeRemoved(HttpSessionBindingEvent sbe) {
        /* This method is called when an attribute is removed from a session. */
    }

    @Override
    public void attributeReplaced(HttpSessionBindingEvent sbe) {
        /* This method is called when an attribute is replaced in a session. */
    }
}
