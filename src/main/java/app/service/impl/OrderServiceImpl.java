package app.service.impl;

import app.exceptions.service.ServiceNotFoundException;
import app.log.Logger;
import app.repository.OrderRep;
import app.repository.entity.order.Order;
import app.service.OrderService;
import app.service.converters.EntityDtoMapper;
import app.service.dto.order.OrderDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRep orderRep;
    private final EntityDtoMapper mapper;

    @Logger
    @Override
    public OrderDto create(OrderDto orderDto) {
        Order order = orderRep.save(mapper.mapToOrder(orderDto));
        return mapper.mapToOrderDto(order);
    }

    @Logger
    @Override
    public Page<OrderDto> getAll(Pageable pageable) {
        return orderRep.findAll(pageable).map(mapper::mapToOrderDto);
    }

    @Logger
    @Override
    public OrderDto get(Long id) {
        return orderRep.findById(id)
                .map(mapper::mapToOrderDto)
                .orElseThrow(() -> new ServiceNotFoundException("Order with id: " + id + "doesn't exist"));
    }

    @Logger
    @Override
    public OrderDto update(OrderDto orderDto) {
        Order order = orderRep.save(mapper.mapToOrder(orderDto));
        return mapper.mapToOrderDto(order);
    }

    @Logger
    @Override
    public void delete(Long id) {
        orderRep.deleteById(id);
    }

    @Logger
    @Override
    public Long count() {
        return orderRep.count();
    }

}
