package com.example.shipping_service.service.impl;

import com.example.shipping_service.dto.OrderDTO;
import com.example.shipping_service.service.KafkaConsumerShippingService;
import com.example.shipping_service.service.KafkaProducerShippingService;
import com.example.shipping_service.service.ShippingService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaConsumerShippingServiceImpl implements KafkaConsumerShippingService {

    private final ObjectMapper objectMapper;
    private final ShippingService shippingService;
    private final KafkaProducerShippingService kafkaProducerShippingService;
    private OrderDTO orderDTO;
    @Override
    @KafkaListener(topics = "payed_orders", groupId = "shipping-group")
    public void consumeOrder(String message) {
        try {
            OrderDTO orderDTO = objectMapper.readValue(message, OrderDTO.class);
            shippingService.processPackaging(orderDTO);
            log.info("Processed payment for order: {}", orderDTO);
            kafkaProducerShippingService.sendMessageFurther(orderDTO);
        } catch (Exception e) {
            log.error("Error processing message: {}", e.getMessage());
            kafkaProducerShippingService.sendMessageError(orderDTO);
        }
    }
}
