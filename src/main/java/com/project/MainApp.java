package com.project;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import com.project.config.AppConfig;

public class MainApp {
    
    private static Logger logger = LogManager.getLogger(MainApp.class);
    public static void main(String[] args) throws LifecycleException {
        
        logger.info("Starting Reimbursement app");

        String docBase = System.getProperty("java.io.tmpdir");

        Tomcat webServer = new Tomcat();
        webServer.setBaseDir(System.getProperty(docBase));
        webServer.setPort(5000);
        webServer.getConnector(); //* server to receive request

        AnnotationConfigWebApplicationContext beanContainer = new AnnotationConfigWebApplicationContext();
        beanContainer.register(AppConfig.class);

        //* Web server context and servlet config
        final String rootContext = "/Reimbursement";
        webServer.addContext(rootContext, docBase);
        webServer.addServlet(rootContext, "DispatcherServlet", new DispatcherServlet(beanContainer)).addMapping("/");

        //* starting and awaiting web request
        webServer.start();

        logger.info("Reimbursement started");

        webServer.getServer().await();
    }
}
