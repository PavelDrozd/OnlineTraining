package App.controller.filter;

import App.controller.ContextListener;
import App.controller.commands.CommandRegister;
import App.service.dto.UserDto;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static App.controller.commands.PagesConstant.INDEX_PAGE;

@WebFilter("/*")
@Component
public class UserRoleFilter extends HttpFilter {

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        String command = req.getParameter("command");
        HttpSession session = req.getSession(false);
        String userRole = getUserRole(session);
        checkRoles(req, res, command, userRole);
        chain.doFilter(req, res);
    }

    private void checkRoles(HttpServletRequest req, HttpServletResponse res, String command, String userRole) throws ServletException, IOException {
        CommandRegister.SecurityLevel userLevel = CommandRegister.SecurityLevel.valueOf(userRole);
        CommandRegister.SecurityLevel commandLevel = CommandRegister.getSecurityLevel(command);
        if (userLevel.ordinal() < commandLevel.ordinal()) {
            req.setAttribute("message", "Insufficient permissions.");
            req.getRequestDispatcher(INDEX_PAGE).forward(req, res);
        }
    }

    private String getUserRole(HttpSession session) {
        String userRole;
        if (session.getAttribute("user") == null) {
            userRole = "USER";
        } else {
            UserDto user = (UserDto) session.getAttribute("user");
            userRole = user.getRoleDto().toString();
        }
        return userRole;
    }

}
