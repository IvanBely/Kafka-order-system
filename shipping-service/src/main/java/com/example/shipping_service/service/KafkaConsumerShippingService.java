package com.example.shipping_service.service;

import com.example.shipping_service.dto.OrderDTO;
import org.apache.kafka.clients.consumer.ConsumerRecord;

public interface KafkaConsumerShippingService {
    void consumeOrder(ConsumerRecord<String, OrderDTO> record);
}
