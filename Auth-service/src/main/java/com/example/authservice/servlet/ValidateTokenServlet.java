package com.example.authservice.servlet;

import com.example.authservice.service.AuthService;
import com.example.authservice.wrapper.ValidationTokenResultWrapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/validate")
public class ValidateTokenServlet extends HttpServlet {
    private final AuthService authService = AuthService.getINSTANCE();
    private static final ObjectMapper mapper = new ObjectMapper();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String token = req.getHeader("Authorization");
        boolean isValid = authService.validateToken(token);
        ValidationTokenResultWrapper result = new ValidationTokenResultWrapper(isValid);
        String json = mapper.writeValueAsString(result);
        resp.setContentType("application/json");
        resp.getWriter().println(json);
        resp.getWriter().flush();
    }
}
