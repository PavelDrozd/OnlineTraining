package app.service.impl;

import app.exceptions.ServiceException;
import app.interceptors.LogInvocation;
import app.repository.OrderRep;
import app.repository.entity.order.Order;
import app.service.OrderService;
import app.service.converters.EntityDtoMapper;
import app.service.dto.order.OrderDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRep orderRep;
    private final EntityDtoMapper mapper;

    @LogInvocation
    @Override
    public OrderDto create(OrderDto orderDto) {
        Order order = orderRep.save(mapper.mapToOrder(orderDto));
        return mapper.mapToOrderDto(order);
    }

    @LogInvocation
    @Override
    public List<OrderDto> findAll(Pageable pageable) {
        List<Order> orders = orderRep.findAll(pageable).stream().toList();
        return orders.stream().map(mapper::mapToOrderDto).collect(Collectors.toList());
    }

    @LogInvocation
    @Override
    public OrderDto findById(Long id) {
        Optional<Order> order = orderRep.findById(id);
        if (order.isEmpty()) {
            throw new ServiceException("Order with id: " + id + "doesn't exist");
        }
        return mapper.mapToOrderDto(order.get());
    }

    @LogInvocation
    @Override
    public OrderDto update(OrderDto orderDto) {
        Order order = orderRep.save(mapper.mapToOrder(orderDto));
        return mapper.mapToOrderDto(order);
    }

    @LogInvocation
    @Override
    public void delete(Long id) {
        Order order = orderRep.findById(id).orElseThrow(() -> new ServiceException("Order doesn't exist"));
    }

    @LogInvocation
    @Override
    public Long count() {
        return orderRep.count();
    }

}
