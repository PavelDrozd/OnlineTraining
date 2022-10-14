package app.controller.commands.user.session;

import app.controller.commands.Command;
import app.service.UserService;
import app.service.dto.UserDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import static app.controller.commands.PagesConstant.INDEX_PAGE;

@Controller
@RequiredArgsConstructor
public class LoginCommand implements Command {
    private final UserService userService;

    @Override
    public String execute(HttpServletRequest req) {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        UserDto user = userService.login(email, password);
        HttpSession  session = req.getSession();
        session.setAttribute("user", user);
        req.setAttribute("message", "Succesfully login!");
        return INDEX_PAGE;
    }
}
