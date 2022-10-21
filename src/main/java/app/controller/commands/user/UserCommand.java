package app.controller.commands.user;

import app.controller.commands.Command;
import app.service.UserService;
import app.service.dto.UserDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import static app.controller.commands.PagesConstant.USER_PAGE;

@Controller
@RequiredArgsConstructor
public class UserCommand implements Command {
    private final UserService userService;

    @Override
    public String execute(HttpServletRequest req) {
        Long id = Long.parseLong(req.getParameter("id"));
        UserDto user = userService.findById(id);
        req.setAttribute("user", user);
        return USER_PAGE;
    }
}
