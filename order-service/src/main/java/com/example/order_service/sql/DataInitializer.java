package com.example.order_service.sql;

import com.example.order_service.model.Product;
import com.example.order_service.model.User;
import com.example.order_service.repository.ProductRepository;
import com.example.order_service.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @PostConstruct
    public void init() {
        // Create products
        createProduct("Phone", 699.99);
        createProduct("Laptop", 1299.99);
        createProduct("Headphones", 199.99);
        createProduct("Keyboard", 49.99);
        createProduct("Mouse", 29.99);

        // Create user
        User user = new User();
        user.setUserEmail("user@user.com");
        userRepository.save(user);
    }

    private void createProduct(String productName, double price) {
        Product product = new Product();
        product.setProductName(productName);
        product.setPrice(price);
        productRepository.save(product);
    }
}

