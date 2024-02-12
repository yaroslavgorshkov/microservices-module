package com.example.catalogservice.util;
import lombok.Getter;
public enum OtherService {
    AUTH(""),
    CART("http://localhost:8082/Catalog_service_war_exploded/cart-servlet"),
    VALIDATION("http://localhost:8085/Validation_service_war_exploded/validation-servlet");

    private final String url;

    public String getUrl() {
        return url;
    }

    OtherService(String url) {
        this.url = url;
    }
}