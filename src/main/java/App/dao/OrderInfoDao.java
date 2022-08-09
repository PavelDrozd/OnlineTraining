package App.dao;

import App.dao.entity.OrderInfo;

import java.util.List;

public interface OrderInfoDao extends AbstractDao<Long, OrderInfo>{

    List<OrderInfo> getByOrderId(Long id);
}
