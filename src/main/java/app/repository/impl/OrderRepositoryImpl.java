package app.repository.impl;

import app.interceptors.LogInvocation;
import app.repository.OrderRepository;
import app.repository.entity.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@RequiredArgsConstructor
@Repository
@Transactional
public class OrderRepositoryImpl implements OrderRepository {
    private static final String FIND_ALL_ORDERS = "from Order where deleted = false";
    private static final String COUNT_ORDERS = "SELECT count(*) AS total FROM orders o WHERE o.deleted = false";

    @PersistenceContext
    private EntityManager manager;

    @LogInvocation
    @Override
    public Order create(Order order) {
        manager.persist(order);
        return order;
    }

    @LogInvocation
    @Override
    public List<Order> getAll(int limit, int offset) {
        return manager.createQuery(FIND_ALL_ORDERS, Order.class)//
                .setMaxResults(limit)//
                .setFirstResult(offset)//
                .getResultList();
    }

    @LogInvocation
    @Override
    public Order getById(Long id) {
        return manager.find(Order.class, id);
    }

    @LogInvocation
    @Override
    public Order update(Order order) {
        manager.merge(order);
        return order;
    }

    @LogInvocation
    @Override
    public void delete(Long id) {
        manager.remove(id);
    }

    @LogInvocation
    @Override
    public Integer count() {
        return manager.createNativeQuery(COUNT_ORDERS, Integer.class).getFirstResult();
    }

}
