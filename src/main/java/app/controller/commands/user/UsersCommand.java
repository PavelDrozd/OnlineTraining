package app.controller.commands.user;

import app.controller.commands.Command;
import app.controller.util.PagingUtil;
import app.service.UserService;
import app.service.dto.UserDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import java.util.List;

import static app.controller.commands.PagesConstant.USERS_PAGE;

@Controller
@RequiredArgsConstructor
public class UsersCommand implements Command {
    private final UserService userService;

    @Override
    public String execute(HttpServletRequest req) {
        PagingUtil.Paging paging = PagingUtil.getPaging(req, userService.count());
        List<UserDto> users = userService.getAll(paging.getLimit(), paging.getOffset());
        req.setAttribute("currentPage", paging.getPage());
        req.setAttribute("totalPages", paging.getTotalPages());
        req.setAttribute("users", users);
        return USERS_PAGE;
    }
}
