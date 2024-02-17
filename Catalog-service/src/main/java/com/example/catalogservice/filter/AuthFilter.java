package com.example.catalogservice.filter;

import com.example.catalogservice.service.AuthService;
import jakarta.servlet.Filter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.FilterChain;

import java.io.IOException;

@WebFilter("/catalog-servlet/*")
public class AuthFilter implements Filter {
    private final AuthService authService = AuthService.getINSTANCE();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String token = httpRequest.getParameter("token");

        if (token != null && authService.isTokenValid(token)) {
            authService.setToken(token);
            if (authService.isAdminUser(token)) {
                chain.doFilter(request, response);
            } else {
                String method = httpRequest.getMethod();
                if ("GET".equals(method)) {
                    chain.doFilter(request, response);
                } else {
                    httpResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    httpResponse.getWriter().write("Permission denied for " + method + " requests");
                }
            }
        } else {
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            httpResponse.getWriter().write("Unauthorized access");
        }
    }
}
