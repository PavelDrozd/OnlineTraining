package App.controller.commands.order;

import App.controller.Command;
import App.service.OrderService;
import App.service.dto.OrderDto;
import jakarta.servlet.http.HttpServletRequest;

import static App.controller.commands.PagesConstant.ORDER_PAGE;

public class OrderCommand implements Command {
    private final OrderService service;

    public OrderCommand(OrderService service) {
        this.service = service;
    }

    @Override
    public String execute(HttpServletRequest req) {
        Long id = Long.parseLong(req.getParameter("id"));
        OrderDto order = service.getById(id);
        req.setAttribute("order", order);
        return ORDER_PAGE;
    }
}
