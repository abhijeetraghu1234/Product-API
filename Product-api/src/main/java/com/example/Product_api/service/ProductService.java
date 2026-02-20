package com.example.Product_api.service;

import com.example.Product_api.entity.Item;
import com.example.Product_api.entity.Product;
import java.util.List;

public interface ProductService {

    Product createProduct(Product product);

    Product updateProduct(Integer id, Product product);

    Product getProductById(Integer id);

    List<Product> getAllProducts();

    void deleteProduct(Integer id);

    List<Item> getProductItems(Integer id);
}
