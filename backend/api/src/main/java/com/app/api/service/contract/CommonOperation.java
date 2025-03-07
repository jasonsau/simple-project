package com.app.api.service.contract;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface CommonOperation<T> {
    Page<T> findAll(Pageable pageable);
    Page<T> findAll(Pageable pageable, Specification<T> specification);
    void save(T t);
    void delete(T t);
    T findById(Long id);
    Long count();
}
