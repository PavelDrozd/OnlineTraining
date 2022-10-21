package app.service;

import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AbstractService<T, K> {

    T create(T t);

    List<T> findAll(Pageable pageable);

    T findById(K id);

    T update(T t);

    void delete(K id);

    Long count();

}
