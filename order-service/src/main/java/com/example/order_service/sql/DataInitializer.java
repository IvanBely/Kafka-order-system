package com.example.order_service.sql;

import com.example.order_service.model.Product;
import com.example.order_service.model.User;
import com.example.order_service.repository.OrderRepository;
import com.example.order_service.repository.ProductRepository;
import com.example.order_service.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class DataInitializer {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    @PostConstruct
    public void init() {
        User user = new User();
        user.setUserEmail("user@user.com");
        userRepository.save(user);

        Product phone = new Product();
        phone.setProductName("Phone");
        phone.setPrice(50_000);

        Product laptop = new Product();
        laptop.setProductName("Laptop");
        laptop.setPrice(150_000);

        Product headphones = new Product();
        headphones.setProductName("Headphones");
        headphones.setPrice(15_000);

        Product keyboard = new Product();
        keyboard.setProductName("Keyboard");
        keyboard.setPrice(5_000);

        Product mouse = new Product();
        mouse.setProductName("Mouse");
        mouse.setPrice(2_000);

        productRepository.saveAll(Arrays.asList(phone, laptop, headphones, keyboard, mouse));
    }
}
