package com.example.shipping_service.service;

public interface KafkaConsumerShippingService {
    void consumeOrder(String message);
}
