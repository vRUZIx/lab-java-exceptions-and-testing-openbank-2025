package com.example.demo.controller;


import com.example.demo.MissingApiKeyException;
import com.example.demo.model.Customer;
import com.example.demo.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {
    private final CustomerService customerService;


    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    private  void checkApiKey(String apiKey){
        if(apiKey == null){
            throw new MissingApiKeyException("Api-key is missing");
        }
        String API_KEY="123456";
        if(!API_KEY.equals(apiKey)){
            throw  new MissingApiKeyException("Invalid Api-key");
        }
    }
    @GetMapping("/{id}")
    public Customer getCustomerById(@RequestHeader("Api-Key") String apiKey, @PathVariable long id){
        checkApiKey(apiKey);
        return customerService.findById(id);
    }

    @GetMapping("/email")
    public Customer getCustomerByEmail(@RequestHeader("Api-Key") String apiKey, @RequestParam String email){
        checkApiKey(apiKey);
        return customerService.findByEmail(email);
    }

    @GetMapping()
    public List<Customer> getAllCustomer(@RequestHeader("Api-Key") String apiKey){
        checkApiKey(apiKey);
        return customerService.findAll();
    }

    @PostMapping
    public ResponseEntity<Customer> createCustomer(@RequestHeader("Api-Key") String apiKey, @Valid @RequestBody Customer customer) {
        checkApiKey(apiKey);
        Customer created = customerService.create(
                customer.getName(), customer.getEmail(), customer.getAge(), customer.getAddress()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }



}
