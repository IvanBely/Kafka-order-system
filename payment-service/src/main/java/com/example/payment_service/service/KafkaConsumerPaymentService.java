package com.example.payment_service.service;

public interface KafkaConsumerPaymentService {
    void consumeOrder(String message);
}
