package com.example.notification_service.service;

import com.example.notification_service.dto.OrderDTO;
import org.apache.kafka.clients.consumer.ConsumerRecord;

public interface KafkaConsumerNotificationsService {
    void consumeOrder(ConsumerRecord<String, OrderDTO> record);
}
