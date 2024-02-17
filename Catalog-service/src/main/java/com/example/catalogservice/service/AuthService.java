package com.example.catalogservice.service;

import com.example.catalogservice.util.OtherService;
import com.example.catalogservice.wrapper.ValidationTokenResultWrapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class AuthService {
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final HttpClient httpClient = HttpClient.newBuilder().build();
    @Getter
    private static final AuthService INSTANCE = new AuthService();
    @Setter
    @Getter
    private String token;

    @SneakyThrows
    public boolean isTokenValid(String token) {
        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.noBody())
                .uri(URI.create(OtherService.AUTH.getUrl()))
                .header("Authorization", token)
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        String jsonString = response.body();
        ValidationTokenResultWrapper result = mapper.readValue(jsonString, ValidationTokenResultWrapper.class);
        return result.isValid();
    }

    public boolean isAdminUser(String token) {
        return token.contains("admin");
    }
}
