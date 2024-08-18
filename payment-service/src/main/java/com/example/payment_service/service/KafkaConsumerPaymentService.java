package com.example.payment_service.service;

import com.example.payment_service.dto.OrderDTO;
import org.apache.kafka.clients.consumer.ConsumerRecord;

public interface KafkaConsumerPaymentService {
    void consumeOrder(ConsumerRecord<String, OrderDTO> record);
}
