package app.service.dto.course;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CourseDto {

    private Long id;

    private String name;

    private String courseInfo;

    private BigDecimal cost;

    private BigDecimal discount;

    private Integer durationDays;

}
