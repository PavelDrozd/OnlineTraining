package app.web.view;

import app.service.OrderService;
import app.service.dto.order.OrderDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Component
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderViewController {

    private final String ATTR_ORDER = "order";

    private final String ORDER_PAGE = "order/order";
    private final String ORDERS_PAGE = "order/orders";
    private final String CREATE_FORM_PAGE = "order/create";
    private final String EDIT_FORM_PAGE = "order/edit";


    private final OrderService orderService;

    @GetMapping("/{id}")
    public String get(@PathVariable Long id, Model model) {
        OrderDto order = orderService.get(id);
        model.addAttribute(ATTR_ORDER, order);
        return ORDER_PAGE;
    }

    @GetMapping
    public String getAll() {
        return ORDERS_PAGE;
    }

    @GetMapping("/create")
    public String create() {
        return CREATE_FORM_PAGE;
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable Long id, Model model) {
        OrderDto order = orderService.get(id);
        model.addAttribute(ATTR_ORDER, order);
        return EDIT_FORM_PAGE;
    }
}
