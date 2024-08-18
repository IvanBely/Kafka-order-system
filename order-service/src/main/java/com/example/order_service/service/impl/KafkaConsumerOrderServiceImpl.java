package com.example.order_service.service.impl;

import com.example.order_service.dto.response.OrderDTO;
import com.example.order_service.service.KafkaConsumerOrderService;
import com.example.order_service.service.OrderService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaConsumerOrderServiceImpl implements KafkaConsumerOrderService {

    private final OrderService orderService;
    @Override
    @KafkaListener(topics = "status_orders", groupId = "orders-group")
    public void consumePayedOrder(ConsumerRecord<String, OrderDTO> record) {
        OrderDTO orderDTO = record.value();
        int partition = record.partition();
        try {
            log.info("Processed for order: {} from partition: {}", orderDTO, partition);
            orderService.updateOrderStatus(orderDTO.getOrderId(), orderDTO.getOrderStatus());
        } catch (Exception e) {
            log.error("Error processing message from partition {}: {}", partition, e.getMessage());
        }
    }
}
