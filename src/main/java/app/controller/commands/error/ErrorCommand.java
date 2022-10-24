package app.controller.commands.error;

import app.controller.commands.Command;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;

import static app.controller.commands.PagesConstant.ERROR_PAGE;

@Controller
public class ErrorCommand implements Command {

    @Override
    public String execute(HttpServletRequest req) {
        return ERROR_PAGE;
    }
}
