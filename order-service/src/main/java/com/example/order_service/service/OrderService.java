package com.example.order_service.service;

import com.example.order_service.dto.request.NewOrder;
import com.example.order_service.dto.response.OrderDTO;
import com.example.order_service.model.Order;

public interface OrderService {
    Order createOrder(NewOrder newOrder);

    OrderDTO createOrderDTO(Order Order);

    void updateOrderStatus(Long orderId, String orderStatus);
}
