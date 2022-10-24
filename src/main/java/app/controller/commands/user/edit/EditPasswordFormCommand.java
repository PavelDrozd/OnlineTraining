package app.controller.commands.user.edit;

import app.controller.commands.Command;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;

import static app.controller.commands.PagesConstant.EDIT_PASSWORD_FORM_PAGE;

@Controller
public class EditPasswordFormCommand implements Command {
    @Override
    public String execute(HttpServletRequest req) {
        return EDIT_PASSWORD_FORM_PAGE;
    }
}
