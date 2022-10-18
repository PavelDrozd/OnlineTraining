package app.controller.commands.order;

import app.controller.commands.Command;
import app.service.OrderService;
import app.service.dto.OrderDto;
import app.service.util.PaginationUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;

import java.util.List;

import static app.controller.commands.PagesConstant.ORDERS_PAGE;

@Controller
@RequiredArgsConstructor
public class OrdersCommand implements Command {
    private final OrderService orderService;
    private final PaginationUtil paginationUtil;

    @Override
    public String execute(HttpServletRequest req) {
        int limit = paginationUtil.getPageSize(req.getParameter("limit"));
        long totalPages = paginationUtil.getTotalPages(limit, orderService.count());
        int page = paginationUtil.getPage(req.getParameter("page"), totalPages);
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        Pageable pageable = PageRequest.of(page - 1, limit, sort);
        List<OrderDto> orders = orderService.findAll(pageable);
        req.setAttribute("currentPage", page);
        req.setAttribute("totalPages", totalPages);
        req.setAttribute("orders", orders);
        return ORDERS_PAGE;
    }
}
