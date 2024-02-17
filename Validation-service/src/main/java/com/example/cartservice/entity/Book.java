package com.example.cartservice.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "catalog_web_module")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String author;
    private String description;
    private double value;
    private int stockQuantity;

    public Book(String name, String author, String description, double value, int stockQuantity) {
        this.name = name;
        this.author = author;
        this.description = description;
        this.value = value;
        this.stockQuantity = stockQuantity;
    }
}
