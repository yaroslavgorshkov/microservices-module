package com.example.catalogservice.database;

import java.util.List;
import java.util.Optional;

public interface DAO<T> {
    void save (T obj);
    void update (T obj);
    void delete (Long id);
    Optional<T> get (Long id);
    List<T> getAll();
}
