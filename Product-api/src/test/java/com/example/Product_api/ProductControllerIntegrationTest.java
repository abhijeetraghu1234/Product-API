package com.example.Product_api;
import com.example.Product_api.entity.Item;
import com.example.Product_api.entity.Product;
import com.example.Product_api.repository.ProductRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
//@TestPropertySource(properties = "spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration")
public class ProductControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        productRepository.deleteAll(); // clean DB before each test
    }
    


    @Test
    void testCreateAndGetProduct() throws Exception {
        Product product = new Product();
        product.setProductName("Laptop");

        // Create Product
        String json = objectMapper.writeValueAsString(product);
        mockMvc.perform(post("/api/v1/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productName").value("Laptop"));

        // Fetch Product
        Product saved = productRepository.findAll().get(0);
        mockMvc.perform(get("/api/v1/products/" + saved.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productName").value("Laptop"));
    }

    @Test
    void testGetAllProducts() throws Exception {
        Product p1 = new Product();
        p1.setProductName("A");
        Product p2 = new Product();
        p2.setProductName("B");
        productRepository.save(p1);
        productRepository.save(p2);

        mockMvc.perform(get("/api/v1/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

@Test
void testGetItemsOfProduct() throws Exception {

    Product product = new Product();
    product.setProductName("Laptop");
    product.setCreatedOn(LocalDateTime.now());
    product.setModifiedOn(LocalDateTime.now());

    Item item = new Item();
    item.setQuantity(2);

    product.addItem(item);

    Product savedProduct = productRepository.save(product);   // IMPORTANT

    mockMvc.perform(get("/api/v1/products/" + savedProduct.getId() + "/items"))
            .andExpect(status().isOk());
}

    @Test
    void testDeleteProduct() throws Exception {
        Product product = new Product();
        product.setProductName("ToDelete");
        productRepository.save(product);

        mockMvc.perform(delete("/api/v1/products/" + product.getId()))
                .andExpect(status().isOk())
                .andExpect(content().string("Deleted Successfully"));
    }
}