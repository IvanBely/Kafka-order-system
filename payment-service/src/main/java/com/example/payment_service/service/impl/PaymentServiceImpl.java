package com.example.payment_service.service.impl;

import com.example.payment_service.dto.OrderDTO;
import com.example.payment_service.exception.PaymentException;
import com.example.payment_service.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PaymentServiceImpl implements PaymentService {

    private boolean paymentSuccessful = true;

    @Override
    public OrderDTO processPayment(OrderDTO orderDTO) {
//        paymentSuccessful = !paymentSuccessful;
        if (paymentSuccessful) {
            orderDTO.setOrderStatus("PAYED");
            log.info("Payment successful for order ID: {}", orderDTO.getOrderId());
            return orderDTO;
        } else {
            log.error("Payment failed for order ID: {}", orderDTO.getOrderId());
            throw new PaymentException("Payment failed for order with ID: " + orderDTO.getOrderId());
        }
    }
}
