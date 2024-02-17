package com.example.catalogservice.servlet;

import java.io.*;

import com.example.catalogservice.service.DeliveryService;
import com.example.catalogservice.util.DeliveryStatus;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet("/delivery-servlet")
public class DeliveryServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        DeliveryService deliveryService = new DeliveryService();
        DeliveryStatus deliveryStatus = deliveryService.getDeliveryStatus();

        response.setContentType("text/html");

        PrintWriter out = response.getWriter();
        out.println("<html><body>");

        if (deliveryStatus == DeliveryStatus.CAN_BE_DELIVERED) {
            out.println("<h1 style=\"color:green;\">Goods shipped! Thank you for using our service!</h1>");
        } else {
            out.println("<h1 style=\"color:red;\">The item will be shipped after 7:00 the next day!</h1>");
        }

        out.println("</body></html>");
    }
}
