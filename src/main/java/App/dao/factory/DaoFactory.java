package App.dao.factory;

import App.dao.CourseDao;
import App.dao.OrderDao;
import App.dao.OrderInfoDao;
import App.dao.UserDao;
import App.dao.connection.DataSource;
import App.dao.impl.CourseDaoImpl;
import App.dao.impl.OrderDaoImpl;
import App.dao.impl.OrderInfoDaoImpl;
import App.dao.impl.UserDaoImpl;

import java.util.HashMap;
import java.util.Map;

public enum DaoFactory {
    INSTANCE;
    private final Map<Class<?>, Object> map;

    DaoFactory() {
        map = new HashMap<>();
        map.put(UserDao.class, new UserDaoImpl(DataSource.INSTANCE));
        map.put(CourseDao.class, new CourseDaoImpl(DataSource.INSTANCE));
        map.put(OrderInfoDao.class, new OrderInfoDaoImpl(DataSource.INSTANCE, getDao(CourseDao.class)));
        map.put(OrderDao.class, new OrderDaoImpl(DataSource.INSTANCE, getDao(UserDao.class), getDao(OrderInfoDao.class)));
    }

    @SuppressWarnings("unchecked")
    public <T> T getDao(Class<T> clazz) {
        T dao = (T) map.get(clazz);
        if (dao == null) {
            throw new RuntimeException("Class " + clazz + " is not constructed.");
        }
        return dao;
    }
}
