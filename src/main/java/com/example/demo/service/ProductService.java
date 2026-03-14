package com.example.demo.service;

import com.example.demo.ProductNotFoundException;
import com.example.demo.model.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProductService {

    private final Map<Long, Product> products = new HashMap<>();
    private Long nextId = 1L;

    public List<Product> findAll() {
        return new ArrayList<>(products.values());
    }

    public Product findById(Long id) {
        Product product = products.get(id);
        if (product == null) {
            throw new ProductNotFoundException("Product with id " + id + " not found");
        }
        return product;
    }

    public List<Product> findByName(String name) {
        return products.values().stream()
                .filter(p -> p.getName().toLowerCase().contains(name.toLowerCase()))
                .toList();
    }

    public List<Product> findByCategory(String category) {
        return products.values().stream()
                .filter(p -> p.getCategory().toLowerCase().contains(category.toLowerCase()))
                .toList();
    }

    public List<Product> findByPriceRange(double min, double max) {
        return products.values()
                .stream()
                .filter(p -> p.getPrice() >= min && p.getPrice() <= max)
                .toList();
    }

    public Product create(String name, double price, String category, int quantity) {
        Product product = new Product(nextId++, name, price, category, quantity);
        products.put(product.getId(), product);
        return product;
    }

    public Product fullUpdate(String name, Product newValues) {
        Product existing = findSingleByName(name);
        existing.setName(newValues.getName());
        existing.setCategory(newValues.getCategory());
        existing.setQuantity(newValues.getQuantity());
        existing.setPrice(newValues.getPrice());
        return existing;
    }

    public Product partialUpdate(String name, Product updates) {
        Product existing = findSingleByName(name);

        if (updates.getName() != null) {
            existing.setName(updates.getName());
        }
        if (updates.getCategory() != null) {
            existing.setCategory(updates.getCategory());
        }
        if (updates.getQuantity() >= 0) {
            existing.setQuantity(updates.getQuantity());
        }
        if (updates.getPrice() >= 0) {
            existing.setPrice(updates.getPrice());
        }

        return existing;
    }

    public Product delete(String name) {
        Product existing = findSingleByName(name);
        products.remove(existing.getId());
        return existing;
    }

    private Product findSingleByName(String name) {
        return products.values().stream()
                .filter(p -> p.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElseThrow(() ->
                        new ProductNotFoundException("Product with name '" + name + "' not found")
                );
    }
}
