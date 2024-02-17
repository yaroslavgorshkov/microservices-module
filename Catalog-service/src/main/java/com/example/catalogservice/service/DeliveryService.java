package com.example.catalogservice.service;


import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.temporal.ChronoUnit;

import com.example.catalogservice.wrapper.DeliveryStatusWrapper;
import com.example.catalogservice.util.DeliveryStatus;
import com.example.catalogservice.util.OtherService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

public class DeliveryService {
    private final ObjectMapper mapper = new ObjectMapper();
    private final HttpClient httpClient = HttpClient.newBuilder().build();

    @SneakyThrows
    public DeliveryStatus getDeliveryStatus() {
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(OtherService.DELIVERY.getUrl()))
                .timeout(Duration.of(10, ChronoUnit.SECONDS))
                .header("Content-Type", "application/json")
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        String jsonString = response.body();

        DeliveryStatusWrapper deliveryStatusWrapper = mapper.readValue(jsonString, DeliveryStatusWrapper.class);
        DeliveryStatus deliveryStatus = deliveryStatusWrapper.getDeliveryStatus();
        return deliveryStatus;
    }
}