package com.example.notification_service.service;

import com.example.notification_service.dto.OrderDTO;

public interface KafkaProducerNotificationsService {
    void sendMessageBack (OrderDTO orderDTO);
}
