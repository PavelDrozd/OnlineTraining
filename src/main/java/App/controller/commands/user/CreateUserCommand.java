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
