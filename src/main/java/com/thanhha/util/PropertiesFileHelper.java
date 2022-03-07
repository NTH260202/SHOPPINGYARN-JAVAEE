package com.thanhha.util;

import javax.servlet.ServletContext;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static com.sun.activation.registries.LogSupport.log;

public class PropertiesFileHelper {
    public static Properties getProperties(ServletContext context, String fileRelativePath){
        InputStream input = context.getResourceAsStream(fileRelativePath);
        Properties property = null;
        try {
            property = new Properties();
            property.load(input);
        } catch (IOException ex) {
            log("PropertiesFileHelper " + ex.getMessage());
        }
        return property;
    }
}
