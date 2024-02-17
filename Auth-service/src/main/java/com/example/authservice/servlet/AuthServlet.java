package com.example.authservice.servlet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/auth-servlet")
public class AuthServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<html><body>");
        out.println("<h2>Authorization Form</h2>");
        out.println("<form method=\"get\" action=\"/Auth_service/validate-auth-servlet\">");
        out.println("Username: <input type=\"text\" name=\"username\" placeholder=\"Enter your username\"><br>");
        out.println("Password: <input type=\"password\" name=\"password\" placeholder=\"Enter your password\"><br>");
        out.println("<input type=\"submit\" value=\"Submit\">");
        out.println("</form>");
        out.println("</body></html>");
    }
}
