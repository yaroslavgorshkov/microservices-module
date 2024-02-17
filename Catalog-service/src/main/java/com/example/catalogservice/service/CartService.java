package com.example.catalogservice.service;

import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class CartService {
    private final List<String> cart = new ArrayList<>();

    public void addBookToCart(String book) {
        cart.add(book);
    }

    public String getFormattedCartContents() {
        if (cart.isEmpty()) {
            return "Your Cart: empty";
        } else {
            Map<String, Integer> bookQuantities = new HashMap<>();

            for (String book : cart) {
                bookQuantities.put(book, bookQuantities.getOrDefault(book, 0) + 1);
            }

            StringBuilder formattedContents = new StringBuilder();
            formattedContents.append("Your Cart: ");
            for (Map.Entry<String, Integer> entry : bookQuantities.entrySet()) {
                String book = entry.getKey();
                int quantity = entry.getValue();
                formattedContents.append("\"").append(book).append("\", ").append(quantity).append("; ");
            }
            return formattedContents.toString();
        }
    }
}
