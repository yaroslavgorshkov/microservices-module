package com.example.catalogservice.service;

import com.example.catalogservice.database.DAO;
import com.example.catalogservice.database.DBBookHibernateDAO;
import com.example.catalogservice.entity.Book;

import java.util.List;
import java.util.Optional;


public class CatalogService {
    private final DAO<Book> bookDAO = new DBBookHibernateDAO();

    public void save(Book book) {
        bookDAO.save(book);
    }

    public void update(Book book) {
        bookDAO.update(book);
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
