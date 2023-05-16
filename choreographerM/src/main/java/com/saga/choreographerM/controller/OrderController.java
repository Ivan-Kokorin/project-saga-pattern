package com.saga.choreographerM.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.saga.choreographerM.Utils.StatusRq;
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
    public String createOrder(@RequestBody OrderDto orderDto) throws JsonProcessingException, InterruptedException {
        orderDto.setCreated(LocalDateTime.now());
        orderDto.setIdRequest(UtilsClient.createIdRequest(orderDto.getCreated()));
        orderDto.setStatus(StatusRq.START_PROCESSING_ORDER.getStatus());
        UtilsClient.statusRequest.put(orderDto.getIdRequest(), StatusRq.START_PROCESSING_ORDER);
        log.info("create order request received " + orderDto);
        orderService.sendForProcessing(orderDto, ProducerTopic.ORDER);
        //todo write a better implementation of expectation
        while (UtilsClient.statusRequest.get(orderDto.getIdRequest()) != StatusRq.WAITING_FOR_PAYMENT && UtilsClient.statusRequest.get(orderDto.getIdRequest()) != StatusRq.DELETED_ORDER) {
            Thread.sleep(1000);
        }
        if (UtilsClient.statusRequest.get(orderDto.getIdRequest()) == StatusRq.WAITING_FOR_PAYMENT) {
            return StatusRq.WAITING_FOR_PAYMENT.getStatus();
        }
        return StatusRq.FAIL_CHECKED_PRODUCT.getStatus();
    }
}
