package App.dao.entity;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Course {
    private Long id;
    private String name;
    private BigDecimal cost;
    private Integer durationDays;
}
