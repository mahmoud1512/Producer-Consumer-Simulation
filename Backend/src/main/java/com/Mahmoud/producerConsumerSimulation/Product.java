package com.Mahmoud.producerConsumerSimulation;

public class Product {
    private final String color;
    private final String name;

    public String getName() {
        return name;
    }

    public Product(String color, String name) {
        this.color = color;
        this.name = name;
    }

    public String getColor() {
        return color;
    }
}
