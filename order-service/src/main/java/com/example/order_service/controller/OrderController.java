package com.example.order_service.controller;

import com.example.order_service.dto.request.NewOrder;
import com.example.order_service.dto.response.OrderDTO;
import com.example.order_service.model.Order;
import com.example.order_service.service.KafkaConsumerOrderService;
import com.example.order_service.service.KafkaProducerOrderService;
import com.example.order_service.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final KafkaProducerOrderService kafkaProducerOrderService;
    private final KafkaConsumerOrderService kafkaConsumerOrderService;
    @PostMapping("/create")
    public ResponseEntity<String> createOrder(@RequestBody NewOrder newOrder) {
        Order order = orderService.createOrder(newOrder);
        OrderDTO OrderDTO = orderService.createOrderDTO(order);
        kafkaProducerOrderService.sendMessage(OrderDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("Order created successfully");
    }
//    @PostMapping("/update")
//    public ResponseEntity<String> updateOrder(@RequestBody NewOrder newOrder) {
//        kafkaProducerService.sendMessageWithKey(newOrder);
//        return ResponseEntity.status(HttpStatus.OK).body("Order updated successfully");
//    }
//
}
