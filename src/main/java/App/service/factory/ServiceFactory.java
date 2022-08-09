package App.service.factory;

import App.dao.CourseDao;
import App.dao.OrderDao;
import App.dao.factory.DaoFactory;
import App.dao.UserDao;
import App.service.CourseService;
import App.service.OrderService;
import App.service.UserService;
import App.service.impl.CourseServiceImpl;
import App.service.impl.OrderServiceImpl;
import App.service.impl.UserServiceImpl;

import java.util.HashMap;
import java.util.Map;

public enum ServiceFactory {
    INSTANCE;
    private final Map<Class<?>, Object> map;

    ServiceFactory() {
        map = new HashMap<>();
        map.put(UserService.class, new UserServiceImpl(DaoFactory.INSTANCE.getDao(UserDao.class)));
        map.put(CourseService.class, new CourseServiceImpl(DaoFactory.INSTANCE.getDao(CourseDao.class)));
        map.put(OrderService.class, new OrderServiceImpl(
                DaoFactory.INSTANCE.getDao(OrderDao.class), DaoFactory.INSTANCE.getDao(CourseDao.class)));
    }
    @SuppressWarnings("unchecked")
    public <T> T getService(Class<T> clazz) {
        return (T) map.get(clazz);
    }
}
