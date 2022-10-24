package app.controller.commands.user;

import app.controller.commands.Command;
import app.service.UserService;
import app.service.dto.UserDto;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import static app.controller.commands.PagesConstant.USER_PAGE;

@Controller
@RequiredArgsConstructor
public class CreateUserCommand implements Command {
    private final UserService userService;

    @Override
    public String execute(HttpServletRequest req) {
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        Integer age = Integer.parseInt(req.getParameter("age"));
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        UserDto user = new UserDto();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setAge(age);
        user.setEmail(email);
        user.setPassword(password);
        UserDto created = userService.create(user);
        req.setAttribute("user", created);
        req.setAttribute("message", "New user was created!");
        return USER_PAGE;
    }
}
