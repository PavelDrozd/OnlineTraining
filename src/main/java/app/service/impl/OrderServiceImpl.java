package app.service.impl;

import app.interceptors.LogInvocation;
import app.repository.CourseRep;
import app.repository.OrderRep;
import app.repository.entity.Course;
import app.repository.entity.Order;
import app.repository.entity.OrderInfo;
import app.repository.entity.User;
import app.exceptions.ServiceException;
import app.service.OrderService;
import app.service.dto.OrderDto;
import app.service.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRep orderRep;
    private final CourseRep courseRep;

    @LogInvocation
    @Override
    public OrderDto create(OrderDto orderDto) {
        Order order = orderRep.save(toOrderEntity(orderDto));
        return toOrderDto(order);
    }

    @LogInvocation
    @Override
    public List<OrderDto> findAll(Pageable pageable) {
        List<Order> orders = orderRep.findByDeletedFalse(pageable).stream().toList();
        return orders.stream().map(this::toOrderDto).collect(Collectors.toList());
    }

    @LogInvocation
    @Override
    public OrderDto findById(Long id) {
        Optional<Order> order = orderRep.findByIdAndDeletedFalse(id);
        if (order.isEmpty()) {
            throw new ServiceException("Order with id: " + id + "doesn't exist");
        }
        return toOrderDto(order.get());
    }

    @LogInvocation
    @Override
    public OrderDto update(OrderDto orderDto) {
        Order order = orderRep.save(toOrderEntity(orderDto));
        return toOrderDto(order);
    }

    @LogInvocation
    @Override
    public void delete(Long id) {
        Order order = orderRep.findById(id).orElseThrow(() -> new ServiceException("Order doesn't exist"));
        order.setDeleted(false);
    }

    @LogInvocation
    @Override
    public Long count() {
        return orderRep.count();
    }

    @LogInvocation
    @Override
    public OrderDto processCart(Map<Long, Integer> cart, UserDto userDto) {
        return createOrderEntity(cart, userDto);
    }

    private OrderDto createOrderEntity(Map<Long, Integer> cart, UserDto userDto) {
        OrderDto orderDto = new OrderDto();
        orderDto.setUser(userDto);
        orderDto.setStatus(OrderDto.StatusDto.UNPAID);
        List<OrderInfo> details = new ArrayList<>();
        cart.forEach((courseId, quantity) -> {
            OrderInfo orderInfo = new OrderInfo();
            Optional<Course> course = courseRep.findById(courseId);
            orderInfo.setCourse(course.orElseThrow(() -> new ServiceException("Course doesn't exist")));
            orderInfo.setCoursePrice(course.get().getCost());
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
        Optional<Order> orderByUser = orderRep.findById(orderDto.getUser().getId());
        order.setUser(orderByUser.orElseThrow(() -> new ServiceException("OrderByUser doesn't exist")).getUser());
        setOrderStatus(orderDto, order);
        order.setTotalCost(orderDto.getTotalCost());
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
