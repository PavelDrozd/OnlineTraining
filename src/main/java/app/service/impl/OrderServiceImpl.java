package app.service.impl;

import app.exceptions.ServiceException;
import app.interceptors.LogInvocation;
import app.repository.OrderRep;
import app.repository.entity.order.Order;
import app.service.OrderService;
import app.service.converters.EntityDtoMapper;
import app.service.dto.order.OrderDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
    public Page<OrderDto> getAll(Pageable pageable) {
        return orderRep.findAll(pageable).map(mapper::mapToOrderDto);
    }

    @LogInvocation
    @Override
    public OrderDto get(Long id) {
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
        orderRep.deleteById(id);
    }

    @LogInvocation
    @Override
    public Long count() {
        return orderRep.count();
    }

}
