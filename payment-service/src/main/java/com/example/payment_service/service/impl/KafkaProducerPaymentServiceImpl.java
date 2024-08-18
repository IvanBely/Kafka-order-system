package com.example.payment_service.service.impl;

import com.example.payment_service.dto.OrderDTO;
import com.example.payment_service.service.KafkaProducerPaymentService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaProducerPaymentServiceImpl implements KafkaProducerPaymentService {

    private final KafkaTemplate<String, OrderDTO> kafkaTemplate;
    private static final String PAYED_TOPIC = "payed_orders";
    private static final String STATUS_TOPIC = "status_orders";

    @Override
    public void sendMessageFurther(OrderDTO orderDTO) {
        try {
            kafkaTemplate.send(PAYED_TOPIC, orderDTO);
            log.info("Sent message to Kafka: {}", orderDTO);
            sendMessageBack(orderDTO);
        } catch (Exception e) {
            log.error("Error serializing message: {}", e.getMessage());
        }
    }
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

