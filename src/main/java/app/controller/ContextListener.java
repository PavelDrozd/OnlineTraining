//package app.controller;
//
//
//import app.ContextConfig;
//import javax.servlet.ServletContextEvent;
//import javax.servlet.ServletContextListener;
//import javax.servlet.annotation.WebListener;
//import lombok.extern.log4j.Log4j2;
//import org.springframework.context.annotation.AnnotationConfigApplicationContext;
//
//@WebListener
//public class ContextListener implements ServletContextListener {
//    public static AnnotationConfigApplicationContext context;
//
//    @Override
//    public void contextInitialized(ServletContextEvent sce) {
//        context = new AnnotationConfigApplicationContext(ContextConfig.class);
//    }
//
//    @Override
//    public void contextDestroyed(ServletContextEvent sce) {
//        context.close();
//    }
//}
