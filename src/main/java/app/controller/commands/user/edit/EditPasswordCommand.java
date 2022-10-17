package app.controller.commands.user.edit;

import app.controller.commands.Command;
import app.service.UserService;
import app.service.dto.UserDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import static app.controller.commands.PagesConstant.PROFILE_PAGE;

@Controller
@RequiredArgsConstructor
public class EditPasswordCommand implements Command {
    private final UserService userService;

    @Override
    public String execute(HttpServletRequest req) {
        String password = req.getParameter("password");
        HttpSession session = req.getSession(false);
        UserDto user = (UserDto) session.getAttribute("user");
        user.setPassword(password);
        UserDto edited = userService.update(user);
        req.setAttribute("user", edited);
        req.setAttribute("message", "User name has been changed.");
        return PROFILE_PAGE;
    }
}