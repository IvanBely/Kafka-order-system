package com.example.payment_service.service.impl;

import com.example.payment_service.dto.OrderDTO;
import com.example.payment_service.service.KafkaProducerPaymentService;
import com.example.payment_service.service.PaymentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaConsumerPaymentServiceImpl {

    private final PaymentService paymentService;
    private final ObjectMapper objectMapper;
    private final KafkaProducerPaymentService kafkaProducerPaymentService;
    private static final String STATUS_TOPIC = "status_order";
    private OrderDTO orderDTO;

    @KafkaListener(topics = "new_orders", groupId = "payment-group")
    public void consumeOrder(String message) {
        try {
            OrderDTO orderDTO = objectMapper.readValue(message, OrderDTO.class);
            paymentService.processPayment(orderDTO);
            log.info("Processed payment for order: {}", orderDTO);
            kafkaProducerPaymentService.sendMessageFurther(orderDTO);
        } catch (Exception e) {
            log.error("Error processing message: {}", e.getMessage());
            kafkaProducerPaymentService.sendMessageError(orderDTO);
        }
    }
}
