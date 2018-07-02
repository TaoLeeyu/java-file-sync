
package com.treebear.starwifi.web.listener;

import javax.servlet.ServletContextEvent;
import java.io.InputStream;
import java.util.Properties;

/**
 * 扩展contextLoad功能,能够对log4j的配置文件进行替换,实现日志的配置功能
 *
 * @author think4ever
 * @version v 0.1 2014/7/29 18:06 Exp $$
 */
public class ContextLoaderListener extends org.springframework.web.context.ContextLoaderListener {

    public void contextDestroyed(ServletContextEvent sce) {
        super.contextDestroyed(sce);
    }

    public void contextInitialized(ServletContextEvent sce) {
        Properties props = new Properties();
        try {
            InputStream in = ContextLoaderListener.class.getClassLoader().getResourceAsStream("config.properties");
            props.load(in);
            String loggingRoot = props.getProperty("loggingRoot");
            String loggingLevel = props.getProperty("loggingLevel");
            String loggingCharset = props.getProperty("loggingCharset");

            System.setProperty("loggingRoot", loggingRoot);
            System.setProperty("loggingLevel", loggingLevel);
            System.setProperty("loggingCharset", loggingCharset);


        } catch (Exception e) {
            e.printStackTrace();
        }
        super.contextInitialized(sce);
    }
}
