package com.example.cartservice.database;

import com.example.cartservice.entity.Book;
import jakarta.persistence.EntityManager;

import java.util.List;

public class BookDBManager {
    public Book findBookByName(String bookName) {
        try (EntityManager entityManager = ConnectionManager.getEntityManager()) {
            String queryString = "SELECT b FROM Book b WHERE b.name = :bookName";
            List<Book> books = entityManager.createQuery(queryString, Book.class)
                    .setParameter("bookName", bookName)
                    .getResultList();

            if (books.isEmpty()) {
                return null;
            } else {
                return books.get(0);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
