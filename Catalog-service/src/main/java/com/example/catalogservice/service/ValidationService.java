package com.example.catalogservice.service;

import com.example.catalogservice.wrapper.ValidationServiceResponseTypeWrapper;
import com.example.catalogservice.util.OtherService;
import com.example.catalogservice.util.ValidationServiceResponseType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.SneakyThrows;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.temporal.ChronoUnit;

public class ValidationService {
    private final ObjectMapper mapper = new ObjectMapper();
    private final HttpClient httpClient = HttpClient.newBuilder().build();

    @SneakyThrows
    public ValidationServiceResponseType getValidationStatus(String bookName) {
        String urlWithParams = OtherService.VALIDATION.getUrl() + "?bookName=" + URLEncoder.encode(bookName, StandardCharsets.UTF_8);

        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.noBody())
                .uri(URI.create(urlWithParams))
                .timeout(Duration.of(10, ChronoUnit.SECONDS))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        String jsonString = response.body();

        ValidationServiceResponseTypeWrapper receivedWrapper = mapper.readValue(jsonString, ValidationServiceResponseTypeWrapper.class);
        ValidationServiceResponseType receivedResponseType = receivedWrapper.getResponseType();

        return receivedResponseType;
    }
}
