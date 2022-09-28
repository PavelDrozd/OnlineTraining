package App.controller.commands.user.edit;

import App.controller.commands.Command;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;

import static App.controller.commands.PagesConstant.EDIT_PASSWORD_FORM_PAGE;

@Controller
public class EditPasswordFormCommand implements Command {
    @Override
    public String execute(HttpServletRequest req) {
        return EDIT_PASSWORD_FORM_PAGE;
    }
}
