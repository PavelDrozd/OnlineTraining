package app.web.rest;

import app.exceptions.controller.ValidationException;
import app.service.OrderService;
import app.service.dto.order.OrderDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderRestController {

    private final OrderService orderService;

    @GetMapping("/{id}")
    public OrderDto get(@PathVariable Long id) {
        return orderService.get(id);
    }

    @GetMapping
    public Page<OrderDto> getAll(Pageable pageable) {
        return orderService.getAll(pageable);
    }

    @PostMapping
    public ResponseEntity<OrderDto> create(@RequestBody @Valid OrderDto order, Errors errors) {
        checkErrors(errors);
        OrderDto created = orderService.create(order);
        return buildResponseOrder(created);
    }

    @PutMapping("/{id}")
    public OrderDto update(@PathVariable Long id, @RequestBody @Valid OrderDto order, Errors errors) {
        checkErrors(errors);
        order.setId(id);
        return orderService.update(order);
    }

    @PatchMapping("/{id}")
    public OrderDto updatePart(@PathVariable Long id, @RequestBody @Valid OrderDto order) {
        order.setId(id);
        return orderService.update(order);
    }

    private void checkErrors(Errors errors) {
        if (errors.hasErrors()) {
            throw new ValidationException(errors);
        }
    }

    private ResponseEntity<OrderDto> buildResponseOrder(OrderDto order) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .location(getLocation(order))
                .body(order);
    }

    private URI getLocation(OrderDto order) {
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/order/{id}")
                .buildAndExpand(order.getId())
                .toUri();
    }
}
