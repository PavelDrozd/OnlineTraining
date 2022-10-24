//package app.controller;
//
//import app.controller.commands.Command;
//import app.controller.commands.CommandRegister;
//import app.exceptions.ControllerException;
//import app.interceptors.LogInvocation;
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import lombok.extern.log4j.Log4j2;
//
//import java.io.IOException;
//
//import static app.controller.commands.PagesConstant.ERROR_PAGE;
//import static app.controller.commands.PagesConstant.REDIRECT;
//
//@WebServlet("/controller")
//public class Controller extends HttpServlet {
//
//    @Override
//    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
//        process(req, resp);
//    }
//
//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
//        process(req, resp);
//    }
//
//    @LogInvocation
//    private void process(HttpServletRequest req, HttpServletResponse resp) {
//        try {
//            String command = req.getParameter("command");
//            Class<? extends Command> commandDefinition = CommandRegister.getCommand(command);
//            Command commandInstance = ContextListener.context.getBean(commandDefinition);
//            sendResponse(req, resp, commandInstance);
//        } catch (Exception e) {
//            toErrorPage(req, resp);
//        }
//    }
//
//    private void sendResponse(HttpServletRequest req, HttpServletResponse resp, Command commandInstance) throws IOException, ServletException {
//        String page = commandInstance.execute(req);
//        if (page.startsWith(REDIRECT)) {
//            resp.sendRedirect(req.getContextPath() + "/" + page.substring(REDIRECT.length()));
//        } else {
//            req.getRequestDispatcher(page).forward(req, resp);
//        }
//    }
//
//    @LogInvocation
//    private void toErrorPage(HttpServletRequest req, HttpServletResponse resp) {
//        try {
//            req.getRequestDispatcher(ERROR_PAGE).forward(req, resp);
//        } catch (ServletException | IOException ex) {
//            throw new ControllerException(ex);
//        }
//    }
//}
