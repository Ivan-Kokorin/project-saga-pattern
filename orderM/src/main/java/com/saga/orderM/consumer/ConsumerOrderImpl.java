package com.saga.orderM.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.saga.orderM.model.OrderDto;
import com.saga.orderM.producer.ProducerTopic;
import com.saga.orderM.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ConsumerOrderImpl implements Consumer {
    private static final String orderTopic = "${spring.topic-create-order.name}";
    private static final String deleteTopic = "${spring.topic-delete-order.name}";
    private final ObjectMapper objectMapper;
    private final OrderService orderService;

    @Autowired
    public ConsumerOrderImpl(ObjectMapper objectMapper, OrderService orderService) {
        this.objectMapper = objectMapper;
        this.orderService = orderService;
    }

    @Override
    @KafkaListener(topics = orderTopic)
    public void consumeCreateOrderMessage(String message) throws JsonProcessingException {
        log.info("message consumed {}", message);
        OrderDto orderDto = objectMapper.readValue(message, OrderDto.class);
        OrderDto persistedOrderDto = orderService.persistOrder(orderDto);
        orderService.sendForProcessing(persistedOrderDto, ProducerTopic.CHECKED_ORDER);
    }

    @Override
    @KafkaListener(topics = deleteTopic)
    public void consumeDeleteOrderMessage(String message) throws JsonProcessingException {
        log.info("message consumed {}", message);
        OrderDto orderDto = objectMapper.readValue(message, OrderDto.class);
        orderDto.setStatus(StatusOrder.ERROR_CHECKED_PRODUCT.getStatus());
        OrderDto updatedOrderDto = orderService.persistOrder(orderDto);
        orderService.sendForProcessing(updatedOrderDto, ProducerTopic.DELETED_ORDER);
    }
}
