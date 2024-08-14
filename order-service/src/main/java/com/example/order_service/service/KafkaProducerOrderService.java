package com.example.order_service.service;

import com.example.order_service.dto.response.OrderDTO;

public interface KafkaProducerOrderService {
    void sendMessage(OrderDTO OrderDTO);
//    void sendMessageWithKey(String key, String message);
}
