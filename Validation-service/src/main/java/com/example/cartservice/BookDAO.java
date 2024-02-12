package com.example.cartservice;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

public class BookDAO {

    private final EntityManager entityManager;

    public BookDAO() {
        this.entityManager = ConnectionManager.getEntityManager();
    }

    public Book findBookByName(String bookName) {
        String queryString = "SELECT b FROM Book b WHERE b.name = :bookName";
        TypedQuery<Book> query = entityManager.createQuery(queryString, Book.class);
        query.setParameter("bookName", bookName);

        return query.getSingleResult();
    }
}
