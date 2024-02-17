package com.example.cartservice.entity;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class Cart {
    private final Map<String, Integer> userCart = new HashMap<>();
}
