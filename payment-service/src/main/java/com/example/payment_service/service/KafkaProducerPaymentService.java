package com.example.payment_service.service;

import com.example.payment_service.dto.OrderDTO;

public interface KafkaProducerPaymentService {
    void sendMessageBack(OrderDTO orderDTO);
    void sendMessageFurther(OrderDTO orderDTO);

}
