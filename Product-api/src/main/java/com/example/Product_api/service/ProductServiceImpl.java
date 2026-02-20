package com.example.Product_api.service;

import com.example.Product_api.entity.Item;
import com.example.Product_api.entity.Product;
import com.example.Product_api.exception.ResourceNotFoundException;
import com.example.Product_api.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product createProduct(Product product) {
        product.setCreatedOn(LocalDateTime.now());
        product.setModifiedOn(LocalDateTime.now());
        return productRepository.save(product);
    }

    @Override
    public List<Item> getProductItems(Integer id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id " + id));

        return product.getItems();
    }

    @Override
    public Product updateProduct(Integer id, Product productDetails) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id " + id));

        existingProduct.setProductName(productDetails.getProductName());
        existingProduct.setModifiedBy(productDetails.getModifiedBy());
        existingProduct.setModifiedOn(LocalDateTime.now());

        if (productDetails.getItems() != null) {
            existingProduct.getItems().clear();
            productDetails.getItems().forEach(existingProduct::addItem);
        }

        return productRepository.save(existingProduct);
    }

    @Override
    public Product getProductById(Integer id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id " + id));
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public void deleteProduct(Integer id) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id " + id));
        productRepository.delete(existingProduct);
    }
}