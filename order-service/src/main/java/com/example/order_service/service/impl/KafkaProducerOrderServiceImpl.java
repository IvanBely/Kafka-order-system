package com.example.order_service.service.impl;

import com.example.order_service.dto.response.OrderDTO;
import com.example.order_service.service.KafkaProducerOrderService;
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

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;
    private static final String TOPIC = "new_orders";

    @Override
    public void sendMessage(OrderDTO orderDTO) {
        try {
            String message = objectMapper.writeValueAsString(orderDTO);
            kafkaTemplate.send(TOPIC, message);
            log.info("Sent message to Kafka: {}", message);
        } catch (JsonProcessingException e) {
            log.error("Error serializing message: {}", e.getMessage());
        }
    }
}
