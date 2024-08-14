package com.example.shipping_service.service.impl;

import com.example.shipping_service.dto.OrderDTO;
import com.example.shipping_service.exception.PackagingException;
import com.example.shipping_service.service.ShippingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ShippingServiceImpl implements ShippingService {

    private boolean packagingSuccessful = true;

    @Override
    public OrderDTO processPackaging(OrderDTO orderDTO) {
//        packagingSuccessful = !packagingSuccessful;
        if (packagingSuccessful) {
            orderDTO.setOrderStatus("PACKAGED");
            log.info("Packaging successful for order ID: {}", orderDTO.getOrderId());
            return orderDTO;
        } else {
            log.error("Packaging failed for order ID: {}", orderDTO.getOrderId());
            throw new PackagingException("Packaging failed for order with ID: " + orderDTO.getOrderId());
        }
    }
}
