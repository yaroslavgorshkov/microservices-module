package com.example.cartservice;

import java.util.Map;

public class CartService {
    private Map<Book, Integer> booksCapacity;

    public Map<Book, Integer> getBooksCapacity() {
        return booksCapacity;
    }
}
