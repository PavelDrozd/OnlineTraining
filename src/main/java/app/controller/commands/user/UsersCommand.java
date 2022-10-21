package app.controller.commands.user;

import app.controller.commands.Command;
import app.service.UserService;
import app.service.dto.UserDto;
import app.service.util.PaginationUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;

import java.util.List;

import static app.controller.commands.PagesConstant.USERS_PAGE;

@Controller
@RequiredArgsConstructor
public class UsersCommand implements Command {
    private final UserService userService;
    private final PaginationUtil paginationUtil;

    @Override
    public String execute(HttpServletRequest req) {
        int limit = paginationUtil.getPageSize(req.getParameter("limit"));
        long totalPages = paginationUtil.getTotalPages(limit, userService.count());
        int page = paginationUtil.getPage(req.getParameter("page"), totalPages);
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        Pageable pageable = PageRequest.of(page - 1, limit, sort);
        List<UserDto> users = userService.findAll(pageable);
        req.setAttribute("currentPage", page);
        req.setAttribute("totalPages", totalPages);
        req.setAttribute("users", users);
        return USERS_PAGE;
    }
}
