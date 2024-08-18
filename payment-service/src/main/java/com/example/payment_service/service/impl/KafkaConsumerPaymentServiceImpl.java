package com.example.payment_service.service.impl;

import com.example.payment_service.dto.OrderDTO;
import com.example.payment_service.service.KafkaConsumerPaymentService;
import com.example.payment_service.service.KafkaProducerPaymentService;
import com.example.payment_service.service.PaymentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaConsumerPaymentServiceImpl implements KafkaConsumerPaymentService {

    private final PaymentService paymentService;
    private final KafkaProducerPaymentService kafkaProducerPaymentService;
    private static final String STATUS_TOPIC = "status_order";
    private OrderDTO orderDTO;
    @Override
    @KafkaListener(topics = "new_orders", groupId = "payment-group")
    public void consumeOrder(ConsumerRecord<String, OrderDTO> record) {
        OrderDTO orderDTO = record.value();
        int partition = record.partition();
        try {
            paymentService.processPayment(orderDTO);
            log.info("Processed for order: {} from partition: {}", orderDTO, partition);
            kafkaProducerPaymentService.sendMessageFurther(orderDTO);
        } catch (Exception e) {
            log.error("Error processing message from partition {}: {}", partition, e.getMessage());
            orderDTO.setOrderStatus("ERROR");
            kafkaProducerPaymentService.sendMessageBack(orderDTO);
        }
    }
}