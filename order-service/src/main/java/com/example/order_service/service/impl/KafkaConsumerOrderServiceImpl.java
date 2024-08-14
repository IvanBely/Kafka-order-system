package com.example.order_service.service.impl;

import com.example.order_service.dto.response.OrderDTO;
import com.example.order_service.service.KafkaConsumerOrderService;
import com.example.order_service.service.OrderService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaConsumerOrderServiceImpl implements KafkaConsumerOrderService {

    private final OrderService orderService;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "status_order", groupId = "orders_group")
    public void consumePayedOrder(String message) {
        log.info("Received payed order message: {}", message);
        try {
            OrderDTO orderDTO = objectMapper.readValue(message, OrderDTO.class);
            orderService.updateOrderStatus(orderDTO.getOrderId(), orderDTO.getOrderStatus());
        } catch (JsonProcessingException e) {
            log.info(e.getMessage());
        }
    }
}
