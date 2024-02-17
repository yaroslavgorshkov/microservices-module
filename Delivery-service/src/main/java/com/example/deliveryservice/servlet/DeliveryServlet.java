package com.example.deliveryservice.servlet;

import java.io.*;

import com.example.deliveryservice.wrapper.DeliveryStatusWrapper;
import com.example.deliveryservice.service.DeliveryService;
import com.example.deliveryservice.util.DeliveryStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet("/delivery-servlet")
public class DeliveryServlet extends HttpServlet {
    private DeliveryService deliveryService;
    private ObjectMapper mapper;

    @Override
    public void init() {
        this.deliveryService = new DeliveryService();
        this.mapper = new ObjectMapper();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        DeliveryStatus deliveryStatus = deliveryService.getDeliveryStatus();
        DeliveryStatusWrapper deliveryStatusWrapper = new DeliveryStatusWrapper();
        deliveryStatusWrapper.setDeliveryStatus(deliveryStatus);

        String jsonResponse = mapper.writeValueAsString(deliveryStatusWrapper);

        response.setContentType("application/json");
        response.getWriter().write(jsonResponse);
    }
}