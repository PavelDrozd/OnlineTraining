package app.controller.commands.user.session;

import app.controller.commands.Command;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;

import static app.controller.commands.PagesConstant.INDEX_PAGE;

@Controller
public class LogoutCommand implements Command {

    @Override
    public String execute(HttpServletRequest req) {
        HttpSession session = req.getSession();
        String lang = (String) session.getAttribute("lang");
        session.invalidate();
        req.getSession().setAttribute("lang", lang);
        return INDEX_PAGE;

    }
}
