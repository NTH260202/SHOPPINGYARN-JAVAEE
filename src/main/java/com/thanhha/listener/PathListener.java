package com.thanhha.listener;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

@WebListener
public class PathListener implements ServletContextListener, HttpSessionListener, HttpSessionAttributeListener {

    public PathListener() {
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        /* This method is called when the servlet context is initialized(when the Web application is deployed). */
        //get path of file
        ServletContext context = sce.getServletContext();
        String realPath = context.getRealPath("/");
        String pathFile = realPath + "WEB-INF/roadmap.txt";
        //read file roadmap
        try {
            File file  = new File(pathFile);
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String details ;
            Map<String, String> pathMap = null;
            while ((details = bufferedReader.readLine()) != null) {
                StringTokenizer token = new StringTokenizer(details, "=");
                String pathName = token.nextToken().trim();
                String pathValue = token.nextToken().trim();
                if (pathMap == null) {
                    pathMap = new HashMap<>();
                }
                pathMap.put(pathName, pathValue);
                context.setAttribute("PATH_MAP", pathMap);
            }
            bufferedReader.close(); fileReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
