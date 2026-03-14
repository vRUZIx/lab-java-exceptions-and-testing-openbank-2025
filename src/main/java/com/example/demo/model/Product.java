package com.example.demo.model;

import jakarta.validation.constraints.*;

public class Product {
    private Long id;

    @NotBlank(message = "Name is required")
    @Size(min = 3, message = "Name must be longer than 3 characters")
    private String name;

    @Positive(message = "Price must be a positive number")
    private double price;

    @NotBlank(message = "Category is required")
    private String category;

    @Min(value = 0, message = "Quantity must be a positive number")
    private int quantity;

    public Product() {}

    public Product(Long id, String name, double price, String category,int quantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.category = category;
        this.quantity=quantity;
    }

    //Getters Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
}
