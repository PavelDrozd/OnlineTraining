package app.web.controllers;

import app.service.OrderService;
import app.service.dto.order.OrderDto;
import app.service.util.PaginationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrdersController {
    private final OrderService orderService;
    private final PaginationUtil paginationUtil;

    @GetMapping("/{id}")
    public String showOrder(@PathVariable Long id, Model model) {
        OrderDto order = orderService.findById(id);
        model.addAttribute("order", order);
        return "order/order";
    }

    @GetMapping("/list")
    public String showOrders(String limit, String page, Model model) {
        int pageLimit = paginationUtil.getPageSize(limit);
        long totalPages = paginationUtil.getTotalPages(pageLimit, orderService.count());
        int pageNumber = paginationUtil.getPage(page, totalPages);
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        Pageable pageable = PageRequest.of(pageNumber - 1, pageLimit, sort);
        List<OrderDto> orders = orderService.findAll(pageable);
        model.addAttribute("currentPage", pageNumber);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("orders", orders);
        return "order/orders";
    }
}
