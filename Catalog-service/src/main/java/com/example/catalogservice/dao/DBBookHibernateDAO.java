package com.example.catalogservice.dao;

import com.example.catalogservice.entity.Book;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

public class DBBookHibernateDAO implements DAO<Book> {

    @Override
    public Book save(Book obj) {
        try (EntityManager entityManager = ConnectionManager.getEntityManager()) {
            try {
                entityManager.getTransaction().begin();
                entityManager.persist(obj);
                entityManager.getTransaction().commit();
            } catch (Exception e) {
                entityManager.getTransaction().rollback();
                throw new RuntimeException(e);
            }
        }
        return obj;
    }

    @Override
    public Book update(Book obj) {
        try (EntityManager entityManager = ConnectionManager.getEntityManager()) {
            try {
                entityManager.getTransaction().begin();
                entityManager.merge(obj);
                entityManager.getTransaction().commit();
            } catch (Exception e) {
                entityManager.getTransaction().rollback();
                throw new RuntimeException(e);
            }
        }
        return obj;
    }

    @Override
    public void delete(Long id) {
        try (EntityManager entityManager = ConnectionManager.getEntityManager()) {
            try {
                entityManager.getTransaction().begin();
                Optional<Book> obj = get(id);
                if (!entityManager.contains(obj.get())) {
                    obj = Optional.of(entityManager.merge(obj.get()));
                }
                entityManager.remove(obj.get());
                entityManager.getTransaction().commit();
            } catch (Exception e) {
                entityManager.getTransaction().rollback();
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public Optional<Book> get(Long id) {
        Optional<Book> obj;
        try (EntityManager entityManager = ConnectionManager.getEntityManager()) {
            try {
                entityManager.getTransaction().begin();
                Book account = entityManager.find(Book.class, id);
                entityManager.getTransaction().commit();
                obj = Optional.ofNullable(account);
            } catch (Exception e) {
                entityManager.getTransaction().rollback();
                throw new RuntimeException(e);
            }
        }
        return obj;
    }

    @Override
    public List<Book> getAll() {
        List<Book> catalog;
        String selectAllQuery = "select b from Book b";
        try (EntityManager entityManager = ConnectionManager.getEntityManager()) {
            try {
                entityManager.getTransaction().begin();
                catalog = entityManager.createQuery(selectAllQuery, Book.class).getResultList();
                entityManager.getTransaction().commit();
            } catch (Exception e) {
                entityManager.getTransaction().rollback();
                throw new RuntimeException(e);
            }
        }
        return catalog;
    }
}
