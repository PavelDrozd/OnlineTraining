package app.repository;

import app.repository.entity.OrderInfo;

import java.util.List;

public interface OrderInfoRepository extends AbstractRepository<Long, OrderInfo> {

    List<OrderInfo> getByOrderId(Long order_id);
}
