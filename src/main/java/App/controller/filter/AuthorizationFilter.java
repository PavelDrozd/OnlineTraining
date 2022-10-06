package App.controller.filter;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;

import java.io.IOException;

@WebFilter("/*")
public class AuthorizationFilter extends HttpFilter {

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
