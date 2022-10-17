package app.controller;

import app.controller.commands.Command;
import app.controller.commands.CommandRegister;
import app.exceptions.ControllerException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;

import static app.controller.commands.PagesConstant.ERROR_PAGE;
import static app.controller.commands.PagesConstant.REDIRECT;

@WebServlet("/controller")
@Log4j2
public class Controller extends HttpServlet {

    @Override
    public void init() {
        log.info("SERVLET INIT");
    }

    @Override
    public void destroy() {
        log.info("SERVLET DESTROY");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        process(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        process(req, resp);
    }

    private void process(HttpServletRequest req, HttpServletResponse resp) {
        try {
            String command = req.getParameter("command");
            Class<? extends Command> commandDefinition = CommandRegister.getCommand(command);
            Command commandInstance = ContextListener.context.getBean(commandDefinition);
            sendResponse(req, resp, commandInstance);
        } catch (Exception e) {
            log.error(e);
            toErrorPage(req, resp);
        }
    }

    private void sendResponse(HttpServletRequest req, HttpServletResponse resp, Command commandInstance) throws IOException, ServletException {
        String page = commandInstance.execute(req);
        if (page.startsWith(REDIRECT)) {
            resp.sendRedirect(req.getContextPath() + "/" + page.substring(REDIRECT.length()));
        } else {
            req.getRequestDispatcher(page).forward(req, resp);
        }
    }

    private void toErrorPage(HttpServletRequest req, HttpServletResponse resp) {
        try {
            req.getRequestDispatcher(ERROR_PAGE).forward(req, resp);
        } catch (ServletException | IOException ex) {
            log.error("Can't execute method forward to ERROR_PAGE", ex);
            throw new ControllerException(ex);
        }
    }
}