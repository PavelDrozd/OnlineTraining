package App.service.impl;

import App.dao.CourseDao;
import App.dao.OrderDao;
import App.dao.entity.Course;
import App.dao.entity.Order;
import App.dao.entity.OrderInfo;
import App.dao.entity.User;
import App.service.OrderService;
import App.service.dto.OrderDto;
import App.service.dto.UserDto;
import lombok.extern.log4j.Log4j2;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Log4j2
public class OrderServiceImpl implements OrderService {
    private final OrderDao orderDao;
    private final CourseDao courseDao;

    public OrderServiceImpl(OrderDao orderDao, CourseDao courseDao) {
        this.orderDao = orderDao;
        this.courseDao = courseDao;
    }

    @Override
    public OrderDto create(OrderDto orderDto) {
        log.debug("Service 'create' new userDto: {}.", orderDto);
        Order order = orderDao.create(toOrderEntity(orderDto));
        return toOrderDto(order);
    }

    @Override
    public List<OrderDto> getAll(int limit, long offset) {
        log.debug("Service 'getAll' command request.");
        return orderDao.getAll(limit, offset).stream().map(this::toOrderDto).collect(Collectors.toList());
    }

    @Override
    public OrderDto getById(Long id) {
        log.debug("Service 'getById' id: {}.", id);
        if (orderDao.getById(id) == null) {
            return null;
        }
        return toOrderDto(orderDao.getById(id));
    }

    @Override
    public OrderDto update(OrderDto orderDto) {
        log.debug("Service 'update' userDto: {}.", orderDto);
        Order order = orderDao.update(toOrderEntity(orderDto));
        return toOrderDto(order);
    }

    @Override
    public void delete(Long id) {
        log.debug("Service 'delete' by id: {}.", id);
        if (orderDao.delete(id)){
            throw new RuntimeException("Can't delete order by id: " + id);
        }
    }

    @Override
    public Long count() {
        return orderDao.count();
    }

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
            Course course = courseDao.getById(courseId);
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
        if(order.getId() != null ){
            order.setId(orderDto.getId());
        }
        order.setUser(orderDao.getById(orderDto.getUser().getId()).getUser());
        order.setStatus(Order.Status.values()[orderDto.getStatus().ordinal()]);
        order.setTotalCost(orderDto.getTotalCost());
        order.setDetails(orderDto.getDetails());
        return order;
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
