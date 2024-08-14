package com.example.shipping_service.service;

import com.example.shipping_service.dto.OrderDTO;

public interface ShippingService {
    OrderDTO processPackaging(OrderDTO OrderDTO);
}
