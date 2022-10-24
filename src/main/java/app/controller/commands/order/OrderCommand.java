package app.controller.commands.order;

import app.controller.commands.Command;
import app.service.OrderService;
import app.service.dto.OrderDto;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import static app.controller.commands.PagesConstant.ORDER_PAGE;

@Controller
@RequiredArgsConstructor
public class OrderCommand implements Command {
    private final OrderService service;

    @Override
    public String execute(HttpServletRequest req) {
        Long id = Long.parseLong(req.getParameter("id"));
        OrderDto order = service.findById(id);
        req.setAttribute("order", order);
        return ORDER_PAGE;
    }
}
