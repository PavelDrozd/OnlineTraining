package App.service.dto;

import App.dao.entity.OrderInfo;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class OrderDto {
    private Long id;
    private UserDto user;
    private StatusDto status;
    private BigDecimal totalCost;
    private List<OrderInfo> details;

    public enum StatusDto {
        UNPAID, CANCELLED, PAYED,
    }
}
