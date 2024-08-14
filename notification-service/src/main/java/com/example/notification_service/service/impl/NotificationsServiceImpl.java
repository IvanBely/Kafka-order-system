package com.example.notification_service.service.impl;

import com.example.notification_service.dto.OrderDTO;
import com.example.notification_service.exception.NotificationException;
import com.example.notification_service.service.NotificationsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NotificationsServiceImpl implements NotificationsService {

    private boolean notificationSuccessful = true;

    @Override
    public OrderDTO notificationUser(OrderDTO orderDTO) {
//        notificationSuccessful = !notificationSuccessful;
        if (notificationSuccessful) {
            orderDTO.setOrderStatus("NOTIFIED");
            log.info("Notification successful for order ID: {}", orderDTO.getOrderId());
            return orderDTO;
        } else {
            log.error("Notification failed for order ID: {}", orderDTO.getOrderId());
            throw new NotificationException("Notification failed for order with ID: " + orderDTO.getOrderId());
        }
    }
}