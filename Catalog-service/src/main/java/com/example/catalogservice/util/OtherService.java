package com.example.catalogservice.util;
import lombok.Getter;
@Getter
public enum OtherService {
    DELIVERY("http://localhost:8090/Delivery_service/delivery-servlet"),
    CART("http://localhost:8082/Catalog_service/cart-servlet"),
    VALIDATION("http://localhost:8085/Validation_service/validation-servlet"),
    AUTH("http://localhost:8070/Auth_service/validate");

    private final String url;

    OtherService(String url) {
        this.url = url;
    }
}