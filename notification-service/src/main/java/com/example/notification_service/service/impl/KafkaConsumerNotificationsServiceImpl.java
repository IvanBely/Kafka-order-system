package com.example.notification_service.service.impl;

import com.example.notification_service.dto.OrderDTO;
import com.example.notification_service.service.KafkaConsumerNotificationsService;
import com.example.notification_service.service.KafkaProducerNotificationsService;
import com.example.notification_service.service.NotificationsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
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
    public void consumeOrder(ConsumerRecord<String, OrderDTO> record) {
        OrderDTO orderDTO = record.value();
        int partition = record.partition();
        try {
            notificationsService.notificationUser(orderDTO);
            log.info("Processed for order: {} from partition: {}", orderDTO, partition);
            kafkaProducerNotificationsService.sendMessageBack(orderDTO);
        } catch (Exception e) {
            log.error("Error processing message from partition {}: {}", partition, e.getMessage());
            orderDTO.setOrderStatus("ERROR");
            kafkaProducerNotificationsService.sendMessageBack(orderDTO);
        }
    }
}
