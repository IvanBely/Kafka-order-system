package com.example.shipping_service.service.impl;

import com.example.shipping_service.dto.OrderDTO;
import com.example.shipping_service.service.KafkaConsumerShippingService;
import com.example.shipping_service.service.KafkaProducerShippingService;
import com.example.shipping_service.service.ShippingService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaConsumerShippingServiceImpl implements KafkaConsumerShippingService {

    private final ShippingService shippingService;
    private final KafkaProducerShippingService kafkaProducerShippingService;
    private OrderDTO orderDTO;
    @Override
    @KafkaListener(topics = "payed_orders", groupId = "shipping-group")
    public void consumeOrder(ConsumerRecord<String, OrderDTO> record) {
        OrderDTO orderDTO = record.value();
        int partition = record.partition();
        try {
            shippingService.processPackaging(orderDTO);
            log.info("Processed for order: {} from partition: {}", orderDTO, partition);
            kafkaProducerShippingService.sendMessageFurther(orderDTO);
        } catch (Exception e) {
            log.error("Error processing message from partition {}: {}", partition, e.getMessage());
            orderDTO.setOrderStatus("ERROR");
            kafkaProducerShippingService.sendMessageBack(orderDTO);
        }
    }
}
