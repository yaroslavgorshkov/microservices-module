package com.example.catalogservice.service;

import com.example.catalogservice.dao.DAO;
import com.example.catalogservice.dao.DBBookHibernateDAO;
import com.example.catalogservice.entity.Book;

import java.util.List;
import java.util.Optional;


public class CatalogService {
    private final DAO<Book> bookDAO = new DBBookHibernateDAO();

    public Book save(Book book) {
        return bookDAO.save(book);
    }

    public Book update(Book book) {
        return bookDAO.update(book);
    }

    public void delete(Long id) {
        bookDAO.delete(id);
    }

    public Optional<Book> get(Long id) {
        return bookDAO.get(id);
    }

    public List<Book> getAll() {
        return bookDAO.getAll();
    }
}
