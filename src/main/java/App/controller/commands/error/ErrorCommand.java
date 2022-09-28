package App.controller.commands.error;

import App.controller.commands.Command;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;

import static App.controller.commands.PagesConstant.ERROR_PAGE;

@Controller
public class ErrorCommand implements Command {

    @Override
    public String execute(HttpServletRequest req) {
        return ERROR_PAGE;
    }
}
