package com.example.catalogservice.servlet;

import java.io.*;
import java.util.List;

import com.example.catalogservice.entity.Book;
import com.example.catalogservice.service.CatalogService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet("/catalog-servlet")
public class CatalogServlet extends HttpServlet {
    private CatalogService catalogService;
    private static final ObjectMapper mapper = new ObjectMapper();

    @Override
    public void init() {
        catalogService = new CatalogService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<Book> bookList = catalogService.getAll();
        String convertedResponse = convertToText(bookList);

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html><head><title>Catalog Service</title></head><body>");

        out.println("<h1>Catalog:</h1>");
        out.println("<pre>" + convertedResponse + "</pre>");

        out.println("<form method=\"get\" action=\"/Catalog_service_war_exploded/cart-servlet\">");
        out.println("Введите название книги: <input type=\"text\" name=\"bookName\" placeholder=\"Введите название книги\"><br>");
        out.println("<input type=\"submit\" value=\"Добавить в корзину\">");
        out.println("</form>");

        out.println("</body></html>");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        String name = request.getParameter("name");
        String author = request.getParameter("author");
        String description = request.getParameter("description");
        double value = Double.parseDouble(request.getParameter("value"));
        int stockQuantity = Integer.parseInt(request.getParameter("stockQuantity"));

        Book newBook = new Book(name, author, description, value, stockQuantity);
        catalogService.save(newBook);
        response.setStatus(HttpServletResponse.SC_CREATED);
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) {
        Long id = Long.parseLong(request.getParameter("id"));
        String name = request.getParameter("name");
        String author = request.getParameter("author");
        String description = request.getParameter("description");
        double value = Double.parseDouble(request.getParameter("value"));
        int stockQuantity = Integer.parseInt(request.getParameter("stockQuantity"));

        Book updatedBook = new Book(id, name, author, description, value, stockQuantity);
        catalogService.update(updatedBook);
        response.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) {
        long id = Long.parseLong(request.getParameter("id"));
        catalogService.delete(id);
        response.setStatus(HttpServletResponse.SC_NO_CONTENT);
    }

    private String convertToText(List<Book> products) {
        StringBuilder textBuilder = new StringBuilder();

        for (Book book : products) {
            String bookInfo = String.format("%d. \"%s\", \"%s\", \"%s\", %.2f",
                    book.getId(), book.getName(), book.getAuthor(), book.getDescription(), book.getValue());
            textBuilder.append(bookInfo).append("\n");
        }

        return textBuilder.toString();
    }
}