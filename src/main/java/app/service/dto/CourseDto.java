package app.service.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CourseDto {
    private Long id;
    private String name;
    private BigDecimal cost;
    private Integer durationDays;
}
