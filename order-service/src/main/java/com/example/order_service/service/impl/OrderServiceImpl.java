package com.example.order_service.service.impl;

import com.example.order_service.dto.request.NewOrder;
import com.example.order_service.dto.response.OrderDTO;
import com.example.order_service.exception.InvalidOrderStatusException;
import com.example.order_service.exception.OrderNotFoundException;
import com.example.order_service.model.Order;
import com.example.order_service.model.Product;
import com.example.order_service.model.User;
import com.example.order_service.repository.OrderRepository;
import com.example.order_service.repository.UserRepository;
import com.example.order_service.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    @Override
    public Order createOrder(NewOrder newOrder) {
        String userEmail = newOrder.getUserEmail();

        User user = userRepository.findByUserEmail(userEmail)
                .orElseGet(() -> {
                    User newUser = new User();
                    newUser.setUserEmail(userEmail);
                    return userRepository.save(newUser);
                });

        List<Product> productList = newOrder.getProductList();
        double amount = productList.stream()
                .mapToDouble((Product::getPrice))
                .sum();

        Order order = new Order();
        order.setUser(user);
        order.setCreateTimeOrder(LocalDateTime.now());
        order.setProductList(productList);
        order.setAmount(amount);
        order.setStatus(Order.Status.NEW);
        return orderRepository.save(order);
    }

    @Override
    public OrderDTO createOrderDTO(Order order) {
        OrderDTO OrderDTO = new OrderDTO(
                order.getId(),
                order.getUser().getUserEmail(),
                order.getStatus().name(),
                order.getAmount()
        );
        return OrderDTO;
    }

    @Override
    public void updateOrderStatus (Long orderId, String orderStatus) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with id: " + orderId));
        Order.Status status;
        try {
            status = Order.Status.valueOf(orderStatus.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidOrderStatusException("Invalid order status: " + orderStatus);
        }
        order.setStatus(status);
        orderRepository.save(order);
    }
}
