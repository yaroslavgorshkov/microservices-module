package com.example.catalogservice.servlet;

import com.example.catalogservice.service.AuthService;
import com.example.catalogservice.service.CartService;
import com.example.catalogservice.service.ValidationService;
import com.example.catalogservice.util.ValidationServiceResponseType;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/cart-servlet")
public class CartServlet extends HttpServlet {
    private final ValidationService validationService = new ValidationService();
    private final CartService cartService = new CartService();
    private final AuthService authService = AuthService.getINSTANCE();

    @Override
    protected void doPost (HttpServletRequest request, HttpServletResponse response) throws IOException {
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
            out.println("<p>Cart empty</p>");
        } else {
            out.println("<p>" + formattedCartContents + "</p>");
        }

        out.println("<form method=\"get\" action=\"/Catalog_service/catalog-servlet\">");
        out.println("<input type=\"hidden\" name=\"token\" value=\"" + authService.getToken() + "\">");
        out.println("<input type=\"submit\" value=\"Back to catalog\">");
        out.println("</form>");

        if (!cartService.getCart().isEmpty()) {
            out.println("<form method=\"get\" action=\"/Catalog_service/delivery-servlet\">");
            out.println("<input type=\"submit\" value=\"Confirm order\">");
            out.println("</form>");
        }
        out.println("</body></html>");
    }
}