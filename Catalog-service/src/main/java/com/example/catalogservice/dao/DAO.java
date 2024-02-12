package com.example.catalogservice.dao;

import java.util.List;
import java.util.Optional;

public interface DAO<T> {
    T save (T obj);
    T update (T obj);
    void delete (Long id);
    Optional<T> get (Long id);
    List<T> getAll();
}
