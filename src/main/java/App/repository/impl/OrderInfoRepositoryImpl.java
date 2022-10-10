package App.repository.impl;

import App.repository.OrderInfoRepository;
import App.repository.entity.OrderInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@RequiredArgsConstructor
@Log4j2
@Repository
@Transactional
public class OrderInfoRepositoryImpl implements OrderInfoRepository {
    private static final String FIND_ALL_ORDER_INFOS = "from OrderInfo where deleted = false";
    private static final String FIND_ALL_ORDER_INFOS_BY_ORDER_ID = "from OrderInfo where order_id = :order_id and deleted = false";
    private static final String COUNT_ORDERS_INFOS = "SELECT count(*) AS total FROM order_info i  WHERE i.deleted = false";

    @PersistenceContext
    private EntityManager manager;

    @Override
    public OrderInfo create(OrderInfo orderInfo) {
        log.debug("Database query to the 'INSERT' new order info: {}.", orderInfo);
        manager.persist(orderInfo);
        return orderInfo;
    }

    @Override
    public List<OrderInfo> getAll(int limit, int offset) {
        log.debug("Database query to the 'SELECT' order infos (limit) command");
        return manager.createQuery(FIND_ALL_ORDER_INFOS, OrderInfo.class)//
                .setMaxResults(limit)//
                .setFirstResult(offset)//
                .getResultList();
    }

    @Override
    public OrderInfo getById(Long id) {
        log.debug("Database query to the 'SELECT' order info by id: {}.", id);
        return manager.find(OrderInfo.class, id);
    }

    @Override
    public OrderInfo update(OrderInfo orderInfos) {
        log.debug("Database query to the 'UPDATE' order info parameters to: {}.", orderInfos);
        manager.merge(orderInfos);
        return orderInfos;
    }

    @Override
    public void delete(Long id) {
        log.debug("Database query to the 'UPDATE' delete order info by id: {}.", id);
        manager.remove(id);
    }

    @Override
    public Integer count() {
        log.debug("Database query to the 'COUNT' all order infos.");
        return manager.createNativeQuery(COUNT_ORDERS_INFOS, Integer.class).getFirstResult();
    }

    @Override
    public List<OrderInfo> getByOrderId(Long order_id) {
        log.debug("Database query to the 'SELECT' order_infos by order id: {}.", order_id);
        return manager.createQuery(FIND_ALL_ORDER_INFOS_BY_ORDER_ID, OrderInfo.class)//
                .setParameter("order_id", order_id).getResultList();
    }

}
