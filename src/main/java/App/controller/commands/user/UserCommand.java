package App.controller.commands.user;

import App.controller.Command;
import App.service.UserService;
import App.service.dto.UserDto;
import jakarta.servlet.http.HttpServletRequest;

import static App.controller.commands.PagesConstant.USER_PAGE;

public class UserCommand implements Command {
    private final UserService userService;

    public UserCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest req) {
        Long id = Long.parseLong(req.getParameter("id"));
        UserDto user = userService.getById(id);
        req.setAttribute("user", user);
        return USER_PAGE;
    }
}
