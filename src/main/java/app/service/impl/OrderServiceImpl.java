package app.service.impl;

import app.interceptors.LogInvocation;
import app.repository.CourseRepository;
import app.repository.OrderRepository;
import app.repository.entity.Course;
import app.repository.entity.Order;
import app.repository.entity.OrderInfo;
import app.repository.entity.User;
import app.exceptions.DaoException;
import app.exceptions.ServiceException;
import app.service.OrderService;
import app.service.dto.OrderDto;
import app.service.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final CourseRepository courseRepository;

    @LogInvocation
    @Override
    public OrderDto create(OrderDto orderDto) {
        try {
            Order order = orderRepository.create(toOrderEntity(orderDto));
            return toOrderDto(order);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @LogInvocation
    @Override
    public List<OrderDto> getAll(int limit, int offset) {
        try {
            return orderRepository.getAll(limit, offset).stream().map(this::toOrderDto).collect(Collectors.toList());
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @LogInvocation
    @Override
    public OrderDto getById(Long id) {
        try {
            if (orderRepository.getById(id) == null) {
                return null;
            }
            return toOrderDto(orderRepository.getById(id));
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @LogInvocation
    @Override
    public OrderDto update(OrderDto orderDto) {
        try {
            Order order = orderRepository.update(toOrderEntity(orderDto));
            return toOrderDto(order);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @LogInvocation
    @Override
    public void delete(Long id) {
        orderRepository.delete(id);
    }

    @LogInvocation
    @Override
    public Integer count() {
        try {
            return orderRepository.count();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @LogInvocation
    @Override
    public OrderDto processCart(Map<Long, Integer> cart, UserDto userDto) {
        try {
            return createOrderEntity(cart, userDto);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    private OrderDto createOrderEntity(Map<Long, Integer> cart, UserDto userDto) {
        OrderDto orderDto = new OrderDto();
        orderDto.setUser(userDto);
        orderDto.setStatus(OrderDto.StatusDto.UNPAID);
        List<OrderInfo> details = new ArrayList<>();
        cart.forEach((courseId, quantity) -> {
            OrderInfo orderInfo = new OrderInfo();
            Course course = courseRepository.getById(courseId);
            orderInfo.setCourse(course);
            orderInfo.setCoursePrice(course.getCost());
            details.add(orderInfo);
        });
        orderDto.setDetails(details);
        BigDecimal totalCost = calculatePrice(details);
        orderDto.setTotalCost(totalCost);
        return orderDto;
    }

    private BigDecimal calculatePrice(List<OrderInfo> details) {
        BigDecimal totalCost = BigDecimal.ZERO;
        for (OrderInfo detail : details) {
            BigDecimal coursePrice = detail.getCoursePrice();
            totalCost = totalCost.add(coursePrice);
        }
        return totalCost;
    }

    private Order toOrderEntity(OrderDto orderDto) {
        Order order = new Order();
        setOrderId(orderDto, order);
        order.setUser(orderRepository.getById(orderDto.getUser().getId()).getUser());
        setOrderStatus(orderDto, order);
        order.setTotalCost(orderDto.getTotalCost());
        order.setDetails(orderDto.getDetails());
        return order;
    }

    private static void setOrderId(OrderDto orderDto, Order order) {
        if (order.getId() != null) {
            order.setId(orderDto.getId());
        }
    }

    private static void setOrderStatus(OrderDto orderDto, Order order) {
        if (orderDto.getStatus() == null) {
            orderDto.setStatus(OrderDto.StatusDto.UNPAID);
        }
        order.setStatus(Order.Status.values()[orderDto.getStatus().ordinal()]);
    }

    private OrderDto toOrderDto(Order order) {
        OrderDto orderDto = new OrderDto();
        orderDto.setId(order.getId());
        orderDto.setUser(toUserDto(order.getUser()));
        orderDto.setStatus(OrderDto.StatusDto.values()[order.getStatus().ordinal()]);
        orderDto.setTotalCost(order.getTotalCost());
        orderDto.setDetails(order.getDetails());
        return orderDto;
    }

    private UserDto toUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setAge(user.getAge());
        userDto.setEmail(user.getEmail());
        userDto.setPassword(user.getPassword());
        userDto.setRoleDto(UserDto.RoleDto.values()[user.getRole().ordinal()]);
        return userDto;
    }


}
