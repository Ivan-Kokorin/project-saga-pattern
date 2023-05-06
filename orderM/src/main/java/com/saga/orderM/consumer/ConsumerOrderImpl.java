package com.saga.orderM.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.saga.orderM.model.OrderDto;
import com.saga.orderM.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ConsumerOrderImpl implements Consumer {
    private static final String orderTopic = "${spring.topic.name}";

    private final ObjectMapper objectMapper;
    private final OrderService orderService;

    @Autowired
    public ConsumerOrderImpl(ObjectMapper objectMapper, OrderService orderService) {
        this.objectMapper = objectMapper;
        this.orderService = orderService;
    }

    @Override
    @KafkaListener(topics = orderTopic)
    public void consumeMessage(String message) throws JsonProcessingException {
        log.info("message consumed {}", message);

        OrderDto orderDto = objectMapper.readValue(message, OrderDto.class);
        orderService.persistOrder(orderDto);
    }
}
