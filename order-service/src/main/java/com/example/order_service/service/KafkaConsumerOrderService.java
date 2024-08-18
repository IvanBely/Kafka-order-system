package com.example.order_service.service;

import com.example.order_service.dto.response.OrderDTO;
import org.apache.kafka.clients.consumer.ConsumerRecord;

public interface KafkaConsumerOrderService {
    void consumePayedOrder(ConsumerRecord<String, OrderDTO> record);
}
