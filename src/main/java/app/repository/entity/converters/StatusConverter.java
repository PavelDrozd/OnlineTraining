package app.repository.entity.converters;

import app.repository.entity.order.Order;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class StatusConverter implements AttributeConverter<Order.Status, Integer> {

    @Override
    public Integer convertToDatabaseColumn(Order.Status attribute) {
        return switch (attribute) {
            case UNPAID -> 1;
            case CANCELLED -> 2;
            case PAYED -> 3;
        };
    }

    @Override
    public Order.Status convertToEntityAttribute(Integer dbData) {
        return switch (dbData) {
            case 2 -> Order.Status.CANCELLED;
            case 3 -> Order.Status.PAYED;
            default -> Order.Status.UNPAID;
        };
    }
}
