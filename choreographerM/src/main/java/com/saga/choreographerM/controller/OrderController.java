package com.saga.choreographerM.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.saga.choreographerM.model.OrderDto;
import com.saga.choreographerM.producer.ProducerTopic;
import com.saga.choreographerM.services.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@Slf4j
@RestController
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public String createOrder(@RequestBody OrderDto orderDto) throws JsonProcessingException {
        orderDto.setCreated(LocalDateTime.now());
        orderDto.setStatus("accepted for processing");
        log.info("create order request received" + orderDto);
        return orderService.sendForProcessing(orderDto, ProducerTopic.ORDER);
    }
}