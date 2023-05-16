package com.saga.choreographerM.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.saga.choreographerM.Utils.UtilsClient;
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
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;

    public static Map<String, StatusRq> statusRequest = new HashMap<>();

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public String createOrder(@RequestBody OrderDto orderDto) throws JsonProcessingException, InterruptedException {
        orderDto.setCreated(LocalDateTime.now());
        orderDto.setIdRequest(UtilsClient.createIdRequest(orderDto.getCreated()));
        orderDto.setStatus("accepted for processing");
        statusRequest.put(orderDto.getIdRequest(), StatusRq.CREATE_ORDER);
        log.info("create order request received" + orderDto);
        orderService.sendForProcessing(orderDto, ProducerTopic.ORDER);
        //todo write a better implementation of expectation
        while (statusRequest.get(orderDto.getIdRequest()) != StatusRq.WAITING_FOR_PAYMENT) {
            Thread.sleep(1000);
        }
        return "Waiting for payment...";
    }
}
