package App.controller;

import App.controller.factory.CommandFactory;
import App.dao.connection.DataSource;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static App.controller.commands.PagesConstant.REDIRECT;

@WebServlet("/controller")
public class Controller extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    private void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String command = req.getParameter("command");
        Command commandInstance = CommandFactory.INSTANCE.getCommand(command);
        String page = commandInstance.execute(req);
        if (page.startsWith(REDIRECT)) {
            resp.sendRedirect(req.getContextPath() + "/" + page.substring(REDIRECT.length()));
        } else {
            req.getRequestDispatcher(page).forward(req, resp);
        }
    }

    @Override
    public void destroy() {
        DataSource.INSTANCE.close();
    }
}
