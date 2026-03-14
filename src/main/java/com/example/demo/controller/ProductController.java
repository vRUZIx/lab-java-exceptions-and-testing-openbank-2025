package com.example.demo.controller;

import com.example.demo.InvalidPriceRangeException;
import com.example.demo.MissingApiKeyException;
import com.example.demo.model.Product;
import com.example.demo.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productsService){
        this.productService=productsService;
    }

    private void checkApiKey(String apiKey){
        if(apiKey == null){
            throw new MissingApiKeyException("Api-key is missing");
        }
        String API_KEY = "123456";
        if(!API_KEY.equals(apiKey)){
            throw  new MissingApiKeyException("Invalid Api-key");
        }
    }


    @GetMapping
    public List<Product> getAllProducts(@RequestHeader("Api-Key") String apiKey){
        checkApiKey(apiKey);
        return productService.findAll();
    }

    @GetMapping("/{id}")
    public Product getProductById(@RequestHeader("Api-Key") String apiKey,@PathVariable long id){
        checkApiKey(apiKey);
        return productService.findById(id);
    }

    @GetMapping("/name")
    public List<Product> getProductByName(@RequestHeader("Api-Key") String apiKey,@RequestParam(required = false) String name){
        checkApiKey(apiKey);
        if(name!= null){
            return productService.findByName(name);
        }
        return productService.findAll();
    }

    @GetMapping("/category")
    public List<Product> getProductByCategory(@RequestHeader("Api-Key") String apiKey,@RequestParam String category){
        checkApiKey(apiKey);
        return productService.findByCategory(category);
    }

   @GetMapping("/price-range")
    public List<Product> getProductsByPriceRange(@RequestHeader("Api-Key") String apiKey,
            @RequestParam double min,
            @RequestParam double max) {
        checkApiKey(apiKey);
        if (min < 0 || max < 0 || min > max) {
            throw new InvalidPriceRangeException("Invalid price range");
        }
        return productService.findByPriceRange(min, max);
    }

    @PutMapping("{name}")
    public Product updateProduct(@RequestHeader("Api-Key") String apiKey, @PathVariable  String name, @Valid @RequestBody Product product){
        checkApiKey(apiKey);
     return  productService.fullUpdate(name,product);
    }

    @DeleteMapping("{name}")
    public Product deleteProduct(@RequestHeader("Api-Key") String apiKey, @PathVariable  String name){
        checkApiKey(apiKey);
        return productService.delete(name);
    }
    @PatchMapping("{name}")
    public Product partiallyUpdateProduct(@RequestHeader("Api-Key") String apiKey, @PathVariable  String name, @Valid @RequestBody Product product){
        checkApiKey(apiKey);
        return productService.partialUpdate(name, product);
    }


    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestHeader("Api-Key") String apiKey,
            @Valid @RequestBody Product product) {
        checkApiKey(apiKey);
        Product created = productService.create(
                product.getName(), product.getPrice(), product.getCategory(), product.getQuantity()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
}