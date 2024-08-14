package com.example.notification_service.service;

public interface KafkaConsumerNotificationsService {
    void consumeOrder(String message);
}
