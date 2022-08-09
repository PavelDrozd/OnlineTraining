package App.service;

import App.service.dto.OrderDto;
import App.service.dto.UserDto;

import java.util.Map;

public interface OrderService  extends AbstractService<Long, OrderDto>{

    OrderDto processCart(Map<Long, Integer> cart, UserDto userDto);
}
