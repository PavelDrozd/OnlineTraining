package app.service.converters;

import app.repository.entity.course.Course;
import app.repository.entity.order.Order;
import app.repository.entity.user.User;
import app.service.dto.course.CourseDto;
import app.service.dto.order.OrderDto;
import app.service.dto.user.UserDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EntityDtoMapper {
    private final ModelMapper mapper;

    public CourseDto mapToCourseDto(Course course) {
        return mapper.map(course, CourseDto.class);
    }

    public Course mapToCourse(CourseDto courseDto) {
        return mapper.map(courseDto, Course.class);
    }

    public OrderDto mapToOrderDto(Order order) {
        return mapper.map(order, OrderDto.class);
    }

    public Order mapToOrder(OrderDto orderDto) {
        return mapper.map(orderDto, Order.class);
    }

    public UserDto mapToUserDto(User user) {
        return mapper.map(user, UserDto.class);
    }

    public User mapToUser(UserDto userDto) {
        return mapper.map(userDto, User.class);
    }

}
