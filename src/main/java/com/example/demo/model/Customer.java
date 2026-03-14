package com.example.demo.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class
Customer {
    private Long id;

   @NotBlank(message = "Name is required")
    private String name;

   @NotBlank(message = "Email is required")
   @Email(message = "Please provide a valid email address")
    private String email;

   @Min(value = 18, message = "Age must be greater than 18.")
   private int age;
   @NotBlank(message = "Address is required")
    private String address;

    public Customer() {}

    public Customer(Long id,String name,String email,int age ,String address){
        this.id=id;
        this.name= name;
        this.address=address;
        this.email=email;
        this.age=age;
    }
    //Getters Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
}
