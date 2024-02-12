package com.example.catalogservice.servlet;

import com.example.catalogservice.entity.Book;
import com.example.catalogservice.service.CartService;
import com.example.catalogservice.service.CatalogService;
import com.example.catalogservice.service.ValidationService;
import com.example.catalogservice.util.ValidationServiceResponseType;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/cart-servlet")
public class CartServlet extends HttpServlet {
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final ValidationService validationService = new ValidationService();
    private static final CartService cartService = new CartService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String bookName = request.getParameter("bookName");
        ValidationServiceResponseType responseType = validationService.getValidationStatus(bookName);
        if (responseType == ValidationServiceResponseType.OK) {
            cartService.addBookToCart(bookName);
        }

        String formattedCartContents = cartService.getFormattedCartContents();

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html><head><title>Cart</title></head><body>");
        out.println("<h1>Validation Status: " + responseType.toString().toUpperCase() + "</h1>");

        if (formattedCartContents.isEmpty()) {
            out.println("<p>Корзина пуста</p>");
        } else {
            out.println("<p>" + formattedCartContents + "</p>");
        }

        out.println("</body></html>");
    }

}