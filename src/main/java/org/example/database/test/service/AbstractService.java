package org.example.database.test.service;

import java.util.List;
import java.util.stream.Stream;

public interface AbstractService<T> {
    T add(T entity);
    List<T> addAll(Stream<T> entityStream);
    List<T> findAll();
}
