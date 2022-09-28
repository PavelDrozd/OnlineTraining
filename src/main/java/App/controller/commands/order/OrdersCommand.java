package App.controller.commands.order;

import App.controller.commands.Command;
import App.controller.util.PagingUtil;
import App.service.OrderService;
import App.service.dto.OrderDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import java.util.List;

import static App.controller.commands.PagesConstant.ORDERS_PAGE;

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
