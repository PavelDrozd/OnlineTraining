package app.controller.filter;


import app.interceptors.LogInvocation;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter("/*")
public class AuthorizationFilter extends HttpFilter {

    @LogInvocation
    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        String command = req.getParameter("command");
        if (command == null) {
            req.getRequestDispatcher("index.jsp").forward(req, res);
            return;
        }
        if (requiresAuthorization(command)) {
            HttpSession session = req.getSession();
            if (session == null || session.getAttribute("user") == null) {
                req.setAttribute("message", "Requested page requires authorization");
                req.getRequestDispatcher("jsp/user/loginForm.jsp").forward(req, res);
                return;
            }
        }
        chain.doFilter(req, res);
    }

    private boolean requiresAuthorization(String command) {
        return switch (command) {
            case "create_user_form",
                    "create_user",
                    "login_form",
                    "login",
                    "courses",
                    "course" //
                    -> false;
            default -> true;
        };
    }


}
