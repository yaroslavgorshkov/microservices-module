package com.example.catalogservice.service;

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
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final HttpClient httpClient = HttpClient.newBuilder().build();

    @SneakyThrows
    public ValidationServiceResponseType getValidationStatus(String bookName) {
        ObjectMapper mapper = new ObjectMapper();

        // Формирование объекта запроса в формате JSON
        ObjectNode requestBody = mapper.createObjectNode();
        requestBody.put("bookName", bookName);

        // Отправка запроса
        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(requestBody.toString()))
                .uri(URI.create(OtherService.VALIDATION.getUrl()))
                .timeout(Duration.of(10, ChronoUnit.SECONDS))
                .header("Content-Type", "application/json")
                .build();

        // Получение ответа
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        String jsonString = response.body();

        // Преобразование JSON-строки в объект
        return mapper.readValue(jsonString, ValidationServiceResponseType.class);
    }

}
