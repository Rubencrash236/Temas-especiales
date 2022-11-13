package com.example.tarea2.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter @NoArgsConstructor
public class Product {
    String name;
    String description;
    Double price;

    public Product(String name, String description, Double price){
        this.name = name;
        this.description = description;
        this.price = price;
    }
}
