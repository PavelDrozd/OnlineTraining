package App.repository;

import App.repository.entity.OrderInfo;

import java.util.List;

public interface OrderInfoRepository extends AbstractRepository<Long, OrderInfo> {

    List<OrderInfo> getByOrderId(Long order_id);
}
