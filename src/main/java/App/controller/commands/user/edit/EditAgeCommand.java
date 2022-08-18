package App.controller.commands.user.edit;

import App.controller.Command;
import App.service.UserService;
import App.service.dto.UserDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import static App.controller.commands.PagesConstant.PROFILE_PAGE;

public class EditAgeCommand implements Command {
    private final UserService userService;

    public EditAgeCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest req) {
        Integer age = Integer.parseInt(req.getParameter("age"));
        HttpSession session = req.getSession();
        UserDto user = (UserDto) session.getAttribute("user");
        user.setAge(age);
        UserDto edited = userService.update(user);
        req.setAttribute("user", edited);
        req.setAttribute("message", "The age has been changed.");
        return PROFILE_PAGE;
    }
}
