package app.service;

import app.service.dto.OrderDto;
import app.service.dto.UserDto;

import java.util.Map;

public interface OrderService extends AbstractService<OrderDto, Long> {

    OrderDto processCart(Map<Long, Integer> cart, UserDto userDto);
}
