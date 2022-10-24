package app.controller.commands.user.edit;

import app.controller.commands.Command;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;

import static app.controller.commands.PagesConstant.EDIT_EMAIL_FORM_PAGE;

@Controller
public class EditEmailFormCommand implements Command {
    @Override
    public String execute(HttpServletRequest req) {
        return EDIT_EMAIL_FORM_PAGE;
    }
}
