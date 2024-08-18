package com.example.order_service.service.impl;

import com.example.order_service.dto.response.OrderDTO;
import com.example.order_service.service.KafkaProducerOrderService;
import com.example.order_service.service.OrderService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaProducerOrderServiceImpl implements KafkaProducerOrderService {

    private final KafkaTemplate<String, OrderDTO> kafkaTemplate;
    private final OrderService orderService;
    private static final String TOPIC = "new_orders";

    @Override
    public void sendMessage(OrderDTO orderDTO) {
        try {
            kafkaTemplate.send(TOPIC, orderDTO);
            log.info("Sent message to Kafka: {}", orderDTO);
        } catch (Exception e) {
            log.error("Error serializing message: {}", e.getMessage());
            orderService.updateOrderStatus(orderDTO.getOrderId(), "ERROR");
        }
    }
}
