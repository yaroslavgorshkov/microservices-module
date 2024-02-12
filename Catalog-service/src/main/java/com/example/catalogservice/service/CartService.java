package com.example.catalogservice.service;

import com.example.catalogservice.entity.Book;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class CartService {
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final HttpClient httpClient = HttpClient.newBuilder().build();
    private List<String> cart;


    public void addBookToCart(String book) {
        cart.add(book);
    }

    public String getFormattedCartContents() {
        StringBuilder formattedContents = new StringBuilder();
        formattedContents.append("Your Cart: ");
        for (String book : cart) {
            int quantity = getQuantityOfBook(book);
            formattedContents.append("\"").append(book).append("\", ").append(quantity).append("; ");
        }
        return formattedContents.toString();
    }

    public int getQuantityOfBook(String bookName) {
        int quantity = 0;
        for (String book : cart) {
            if (book.equalsIgnoreCase(bookName)) {
                quantity++;
            }
        }
        return quantity;
    }
}
