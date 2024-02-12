package com.example.cartservice;

import java.io.*;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;


@WebServlet("/validation-servlet")
public class ValidationServlet extends HttpServlet {
    private final CartService cartService = new CartService();
    private static final ObjectMapper mapper = new ObjectMapper();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        ValidationServiceResponseType responseType;

        BufferedReader reader = request.getReader();
        StringBuilder requestBody = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            requestBody.append(line);
        }
        reader.close();

        String bookName = null;
        if (!requestBody.toString().isEmpty()) {
            JsonNode jsonNode = mapper.readTree(requestBody.toString());
            bookName = jsonNode.get("bookName").asText();
        }

        Book book = findBookByName(bookName);

        if (book != null) {
            int currentQuantity = cartService.getBooksCapacity().getOrDefault(book, 0);
            if (currentQuantity < book.getStockQuantity()) {
                cartService.getBooksCapacity().put(book, currentQuantity + 1);
                responseType = ValidationServiceResponseType.OK;
                response.setStatus(HttpServletResponse.SC_OK);
            } else {
                responseType = ValidationServiceResponseType.OUT_OF_STOCK;
                response.setStatus(HttpServletResponse.SC_CONFLICT);
            }
        } else {
            responseType = ValidationServiceResponseType.BOOK_NOT_FOUND;
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }

        String jsonResponse = mapper.writeValueAsString(responseType);
        response.setContentType("application/json");
        response.getWriter().write(jsonResponse);
    }

    private Book findBookByName(String bookName) {
        BookDAO bookDAO = new BookDAO();
        return bookDAO.findBookByName(bookName);
    }
}