package com.example.notification_service.service.impl;

import com.example.notification_service.dto.OrderDTO;
import com.example.notification_service.service.KafkaConsumerNotificationsService;
import com.example.notification_service.service.KafkaProducerNotificationsService;
import com.example.notification_service.service.NotificationsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaConsumerNotificationsServiceImpl implements KafkaConsumerNotificationsService {

    private final ObjectMapper objectMapper;
    private final NotificationsService notificationsService;
    private final KafkaProducerNotificationsService kafkaProducerNotificationsService;
    private OrderDTO orderDTO;
    @Override
    @KafkaListener(topics = "sent_orders", groupId = "notifications-group")
    public void consumeOrder(String message) {
        try {
            OrderDTO orderDTO = objectMapper.readValue(message, OrderDTO.class);
            notificationsService.notificationUser(orderDTO);
            log.info("Processed payment for order: {}", orderDTO);
            kafkaProducerNotificationsService.sendMessageBack(orderDTO);
        } catch (Exception e) {
            log.error("Error processing message: {}", e.getMessage());
            kafkaProducerNotificationsService.sendMessageError(orderDTO);
        }
    }
}
