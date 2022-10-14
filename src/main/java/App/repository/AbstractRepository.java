package App.repository;

import java.util.List;

public interface AbstractRepository<K, T> {

    T create(T t);

    List<T> getAll(int limit, int offset);

    T getById(K id);

    T update(T t);

    void delete(K id);

    Integer count();
}
