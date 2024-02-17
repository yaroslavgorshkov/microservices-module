package com.example.authservice.servlet;

import java.io.*;

import com.example.authservice.service.AuthService;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet("/validate-auth-servlet")
public class ValidateAuthServlet extends HttpServlet {
    private final AuthService authService = AuthService.getINSTANCE();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        String token = authService.authenticate(username, password);

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        if (token != null) {

            out.println("<html><body>");
            out.println("<title>Authorization result</title>");

            out.println("<p>Successful authorization</p>");
            out.println("<form method=\"get\" action=\"http://localhost:8082/Catalog_service/catalog-servlet\">");
            out.println("<input type=\"hidden\" name=\"token\" value=\"" + token + "\">");
            out.println("<input type=\"submit\" value=\"Go to Home\">");
            out.println("</form>");

            if (authService.isAdminUser(token)) {
                out.println("<p>Your token: " + token + "</p>");
            }

        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            out.println("<p>Unauthorized!</p>");
        }
        out.println("</body></html>");
    }
}