package app.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AbstractService<T, K> {

    T create(T t);

    Page<T> findAll(Pageable pageable);

    T findById(K id);

    T update(T t);

    void delete(K id);

    Long count();

}
