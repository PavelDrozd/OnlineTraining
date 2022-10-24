package app.controller.filter;

import app.controller.commands.CommandRegister;
import app.interceptors.LogInvocation;
import app.service.dto.UserDto;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

import static app.controller.commands.PagesConstant.INDEX_PAGE;

@WebFilter("/*")
public class UserRoleFilter extends HttpFilter {

    @LogInvocation
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
