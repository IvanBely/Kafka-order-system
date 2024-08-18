package com.example.shipping_service.service;
import com.example.shipping_service.dto.OrderDTO;

public interface KafkaProducerShippingService {
    void sendMessageFurther (OrderDTO orderDTO);
    void sendMessageBack (OrderDTO orderDTO);
}
