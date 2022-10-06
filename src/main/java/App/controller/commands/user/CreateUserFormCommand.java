package App.controller.commands.user;

import App.controller.commands.Command;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;

import static App.controller.commands.PagesConstant.CREATE_USER_FORM_PAGE;

@Controller
public class CreateUserFormCommand implements Command {

    @Override
    public String execute(HttpServletRequest req) {
        return CREATE_USER_FORM_PAGE;
    }
}
