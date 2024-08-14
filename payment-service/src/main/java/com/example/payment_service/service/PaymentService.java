package com.example.payment_service.service;

import com.example.payment_service.dto.OrderDTO;

public interface PaymentService {
    OrderDTO processPayment(OrderDTO OrderDTO);
}
