package App.service.impl;

import App.repository.CourseRepository;
import App.repository.OrderRepository;
import App.repository.entity.Course;
import App.repository.entity.Order;
import App.repository.entity.OrderInfo;
import App.repository.entity.User;
import App.exceptions.DaoException;
import App.exceptions.ServiceException;
import App.service.OrderService;
import App.service.dto.OrderDto;
import App.service.dto.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Log4j2
@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final CourseRepository courseRepository;

    @Override
    public OrderDto create(OrderDto orderDto) {
        try {
            log.debug("Service 'create' new userDto: {}.", orderDto);
            Order order = orderRepository.create(toOrderEntity(orderDto));
            return toOrderDto(order);
        } catch (DaoException e) {
            log.error("Service can't create 'order': {} , throw: {}", orderDto, e);
            throw new ServiceException(e);
        }
    }

    @Override
    public List<OrderDto> getAll(int limit, int offset) {
        try {
            log.debug("Service 'getAll' command request.");
            return orderRepository.getAll(limit, offset).stream().map(this::toOrderDto).collect(Collectors.toList());
        } catch (DaoException e) {
            log.error("Service can't get all 'orders', throw: {}", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public OrderDto getById(Long id) {
        try {
            log.debug("Service 'getById' id: {}.", id);
            if (orderRepository.getById(id) == null) {
                return null;
            }
            return toOrderDto(orderRepository.getById(id));
        } catch (DaoException e) {
            log.error("Service can't get 'order' by id: {} , throw: {}", id, e);
            throw new ServiceException(e);
        }
    }

    @Override
    public OrderDto update(OrderDto orderDto) {
        try {
            log.debug("Service 'update' userDto: {}.", orderDto);
            Order order = orderRepository.update(toOrderEntity(orderDto));
            return toOrderDto(order);
        } catch (DaoException e) {
            log.error("Service can't update 'order' to: {} , throw: {}", orderDto, e);
            throw new ServiceException(e);
        }
    }

    @Override
    public void delete(Long id) {
        log.debug("Service 'delete' by id: {}.", id);
        orderRepository.delete(id);
    }

    @Override
    public Integer count() {
        try {
            return orderRepository.count();
        } catch (DaoException e) {
            log.error("Service can't count 'orders', throw: {}", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public OrderDto processCart(Map<Long, Integer> cart, UserDto userDto) {
        try {
            return createOrderEntity(cart, userDto);
        } catch (DaoException e) {
            log.error("Service can't create 'order' entity, throw: {}", e);
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
