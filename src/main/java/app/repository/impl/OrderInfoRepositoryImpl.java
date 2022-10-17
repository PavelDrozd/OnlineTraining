package app.repository.impl;

import app.interceptors.LogInvocation;
import app.repository.OrderInfoRepository;
import app.repository.entity.OrderInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@RequiredArgsConstructor
@Repository
@Transactional
public class OrderInfoRepositoryImpl implements OrderInfoRepository {
    private static final String FIND_ALL_ORDER_INFOS = "from OrderInfo where deleted = false";
    private static final String FIND_ALL_ORDER_INFOS_BY_ORDER_ID = "from OrderInfo where order_id = :order_id and deleted = false";
    private static final String COUNT_ORDERS_INFOS = "SELECT count(*) AS total FROM order_info i  WHERE i.deleted = false";

    @PersistenceContext
    private EntityManager manager;

    @LogInvocation
    @Override
    public OrderInfo create(OrderInfo orderInfo) {
        manager.persist(orderInfo);
        return orderInfo;
    }

    @LogInvocation
    @Override
    public List<OrderInfo> getAll(int limit, int offset) {
        return manager.createQuery(FIND_ALL_ORDER_INFOS, OrderInfo.class)//
                .setMaxResults(limit)//
                .setFirstResult(offset)//
                .getResultList();
    }

    @LogInvocation
    @Override
    public OrderInfo getById(Long id) {
        return manager.find(OrderInfo.class, id);
    }

    @LogInvocation
    @Override
    public OrderInfo update(OrderInfo orderInfos) {
        manager.merge(orderInfos);
        return orderInfos;
    }

    @LogInvocation
    @Override
    public void delete(Long id) {
        manager.remove(id);
    }

    @LogInvocation
    @Override
    public Integer count() {
        return manager.createNativeQuery(COUNT_ORDERS_INFOS, Integer.class).getFirstResult();
    }

    @LogInvocation
    @Override
    public List<OrderInfo> getByOrderId(Long order_id) {
        return manager.createQuery(FIND_ALL_ORDER_INFOS_BY_ORDER_ID, OrderInfo.class)//
                .setParameter("order_id", order_id).getResultList();
    }

}
