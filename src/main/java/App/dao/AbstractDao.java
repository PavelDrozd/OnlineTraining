package App.dao;

import java.util.List;

public interface AbstractDao<K, T> {

    T create(T t);

    List<T> getAll(int limit, long offset);

    T getById(K id);

    T update(T t);

    boolean delete(K id);

    K count();
}
