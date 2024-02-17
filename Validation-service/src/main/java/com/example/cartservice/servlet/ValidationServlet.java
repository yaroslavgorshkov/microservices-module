package com.example.cartservice.servlet;

import java.io.*;

import com.example.cartservice.service.ValidationService;
import com.example.cartservice.util.ValidationServiceResponseType;
import com.example.cartservice.wrapper.ValidationServiceResponseTypeWrapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;


@WebServlet("/validation-servlet")
public class ValidationServlet extends HttpServlet {
    private static final ObjectMapper mapper = new ObjectMapper();
    private ValidationService validationService;

    @Override
    public void init() {
        validationService = new ValidationService();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ValidationServiceResponseType responseType;

        String bookName = request.getParameter("bookName");
        if (bookName == null || bookName.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        try {
            responseType = validationService.processBookValidation(bookName);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return;
        }

        ValidationServiceResponseTypeWrapper wrapper = new ValidationServiceResponseTypeWrapper();
        wrapper.setResponseType(responseType);

        response.setContentType("application/json");
        response.getWriter().write(mapper.writeValueAsString(wrapper));
    }
}