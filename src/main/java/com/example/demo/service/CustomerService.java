package com.example.demo.service;

import com.example.demo.model.Customer;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
public class CustomerService {
    private final Map<Long , Customer> customers =new HashMap<>();
    private Long nextId = 1L;
    public List<Customer> findAll(){return  new ArrayList<>(customers.values());}

    public Customer findById(Long id){return customers.get(id);}

    public Customer findByEmail(String email) {
        return customers.values().stream()
                .filter(c -> email.equals(c.getEmail()))
                .findFirst()
                .orElse(null);
    }
    public Customer create(String name,String email,int age,String address){
        Customer customer= new Customer(nextId++,name,email,age,address);
        customers.put(customer.getId(), customer);
        return  customer;
    }

}

