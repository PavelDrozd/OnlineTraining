package App.controller;


import App.ContextConfig;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@WebListener
@Log4j2
public class ContextListener implements ServletContextListener {
    public static AnnotationConfigApplicationContext context;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        log.info("CONTEXT INIT");
        context = new AnnotationConfigApplicationContext(ContextConfig.class);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        log.info("CONTEXT DESTROY");
        context.close();
    }
}
