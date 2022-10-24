package app.controller.commands.user;

import app.controller.commands.Command;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;

import static app.controller.commands.PagesConstant.CREATE_USER_FORM_PAGE;

@Controller
public class CreateUserFormCommand implements Command {

    @Override
    public String execute(HttpServletRequest req) {
        return CREATE_USER_FORM_PAGE;
    }
}
