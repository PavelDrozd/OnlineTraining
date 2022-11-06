package app.service.dto.order;

import app.service.dto.course.CourseDto;
import app.service.dto.user.UserDto;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderDto {

    private Long id;

    private UserDto user;

    private Status status;

    private CourseDto course;

    private BigDecimal totalCost;

    public enum Status {
        UNPAID, CANCELLED, PAYED,
    }
}
