package app.controller.commands.order;

import app.controller.commands.Command;
import app.controller.util.PagingUtil;
import app.service.OrderService;
import app.service.dto.OrderDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import java.util.List;

import static app.controller.commands.PagesConstant.ORDERS_PAGE;

@Controller
@RequiredArgsConstructor
public class OrdersCommand implements Command {
    private final OrderService orderService;

    @Override
    public String execute(HttpServletRequest req) {
        PagingUtil.Paging paging = PagingUtil.getPaging(req, orderService.count());
        List<OrderDto> orders = orderService.getAll(paging.getLimit(), paging.getOffset());
        req.setAttribute("currentPage", paging.getPage());
        req.setAttribute("totalPages", paging.getTotalPages());
        req.setAttribute("orders", orders);
        return ORDERS_PAGE;
    }
}
