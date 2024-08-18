package com.example.notification_service.service.impl;

import com.example.notification_service.dto.OrderDTO;
import com.example.notification_service.service.KafkaProducerNotificationsService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaProducerNotificationsServiceImpl implements KafkaProducerNotificationsService {

    private final KafkaTemplate<String, OrderDTO> kafkaTemplate;
    private final ObjectMapper objectMapper;
    private static final String STATUS_TOPIC = "status_orders";
    @Override
    public void sendMessageBack(OrderDTO orderDTO) {
        try {
            kafkaTemplate.send(STATUS_TOPIC, orderDTO);
            log.info("Sent message to Kafka: {}", orderDTO);
        } catch (Exception e) {
            log.error("Error serializing message: {}", e.getMessage());
        }
    }
}
