package com.example.demo;

import com.example.demo.model.Product;
import com.example.demo.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ProductTestController {

    private static final String API_KEY = "123456";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductService productService;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProductService mockedProductService;

    @Test
    @DisplayName("GET /api/products returns 200 and list of products")
    void getAllProducts_ok() throws Exception {
        productService.create("Laptop", 1200.0, "Electronics", 5);

        mockMvc.perform(get("/api/products")
                        .header("Api-Key", API_KEY))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Laptop"));
    }

    @Test
    @DisplayName("GET /api/products/price-range with invalid range returns 400")
    void getProducts_invalidPriceRange_returnsBadRequest() throws Exception {
        mockMvc.perform(get("/api/products/price-range")
                        .header("Api-Key", API_KEY)
                        .param("min", "10")
                        .param("max", "5"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Invalid price range"));
    }

    @Test
    @DisplayName("POST /api/products without API key returns 401")
    void createProduct_missingApiKey_returnsUnauthorized() throws Exception {
        Product product = new Product(null, "Phone", 800.0, "Electronics", 10);

        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(product)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("GET /api/products/{id} with mocked service returning not found results in 404")
    void getProductById_notFound_withMockBean() throws Exception {
        when(mockedProductService.findById(99L))
                .thenThrow(new ProductNotFoundException("Product with id 99 not found"));

        mockMvc.perform(get("/api/products/99")
                        .header("Api-Key", API_KEY))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Product with id 99 not found"));
    }
}
