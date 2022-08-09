package App.dao.entity;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderInfo {
    private Long id;
    private Course course;
    private Long orderId;
    private BigDecimal coursePrice;
}
