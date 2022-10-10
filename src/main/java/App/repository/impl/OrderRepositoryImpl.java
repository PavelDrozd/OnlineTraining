package App.repository.impl;

import App.repository.OrderRepository;
import App.repository.entity.Order;
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
public class OrderRepositoryImpl implements OrderRepository {
    private static final String FIND_ALL_ORDERS = "from Order where deleted = false";
    private static final String COUNT_ORDERS = "SELECT count(*) AS total FROM orders o WHERE o.deleted = false";

    @PersistenceContext
    private EntityManager manager;

    @Override
    public Order create(Order order) {
        log.debug("Database query to the 'INSERT' new order: {}.", order);
        manager.persist(order);
        return order;
    }

    @Override
    public List<Order> getAll(int limit, int offset) {
        log.debug("Database query to the 'SELECT' orders (limit) command");
        return manager.createQuery(FIND_ALL_ORDERS, Order.class)//
                .setMaxResults(limit)//
                .setFirstResult(offset)//
                .getResultList();
    }

    @Override
    public Order getById(Long id) {
        log.debug("Database query to the 'SELECT' order by id: {}.", id);
        return manager.find(Order.class, id);
    }

    @Override
    public Order update(Order order) {
        log.debug("Database query to the 'UPDATE' order parameters to: {}.", order);
        manager.merge(order);
        return order;
    }

    @Override
    public void delete(Long id) {
        log.debug("Database query to the 'UPDATE' delete order by id: {}.", id);
        manager.remove(id);
    }

    @Override
    public Integer count() {
        log.debug("Database query to the 'COUNT' all orders.");
        return manager.createNativeQuery(COUNT_ORDERS, Integer.class).getFirstResult();
    }

}
