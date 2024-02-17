package com.example.cartservice.service;

import com.example.cartservice.database.BookDBManager;
import com.example.cartservice.entity.Book;
import com.example.cartservice.entity.Cart;
import com.example.cartservice.util.ValidationServiceResponseType;

public class ValidationService {
    private final Cart cart;
    private final BookDBManager bookManager;

    public ValidationService() {
        this.cart = new Cart();
        this.bookManager = new BookDBManager();
    }

    public ValidationServiceResponseType processBookValidation(String bookName) {
        Book book = findBookByName(bookName);
        if (book != null) {
            return validateBookAvailability(book);
        } else {
            return ValidationServiceResponseType.BOOK_NOT_FOUND;
        }
    }

    private Book findBookByName(String bookName) {
        return bookManager.findBookByName(bookName);
    }

    private ValidationServiceResponseType validateBookAvailability(Book book) {
        int currentQuantity = cart.getUserCart().getOrDefault(book.getName(), 0);
        if (currentQuantity < book.getStockQuantity()) {
            cart.getUserCart().put(book.getName(), currentQuantity + 1);
            return ValidationServiceResponseType.OK;
        } else {
            return ValidationServiceResponseType.OUT_OF_STOCK;
        }
    }
}

