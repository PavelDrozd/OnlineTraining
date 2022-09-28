package App.controller.commands.user;

import App.controller.commands.Command;
import App.service.UserService;
import App.service.dto.UserDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import static App.controller.commands.PagesConstant.USER_PAGE;

@Controller
@RequiredArgsConstructor
public class UserCommand implements Command {
    private final UserService userService;

    @Override
    public String execute(HttpServletRequest req) {
        Long id = Long.parseLong(req.getParameter("id"));
        UserDto user = userService.getById(id);
        req.setAttribute("user", user);
        return USER_PAGE;
    }
}
