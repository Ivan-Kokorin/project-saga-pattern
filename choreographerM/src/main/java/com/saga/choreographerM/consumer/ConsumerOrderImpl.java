package com.saga.choreographerM.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.saga.choreographerM.Utils.UtilsClient;
import com.saga.choreographerM.Utils.StatusRq;
import com.saga.choreographerM.producer.ProducerTopic;
import com.saga.choreographerM.services.OrderService;
import com.saga.choreographerM.model.OrderDto;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Data
@Component
public class ConsumerOrderImpl implements Consumer {
    @Autowired
    ApplicationContext applicationContext;
//    private static final String orderCreatedTopic = "${spring.spring.topic-order-created.name.name}";
    private static final String orderCreatedTopic = "${spring.topic-order-created.name}";
    private static final String productCheckedTopic = "${spring.topic-product-checked.name}";
    private static final String productFailCheckedTopic = "${spring.topic-fail-product-checked.name}";
    private static final String orderDeletedTopic = "${spring.topic-deleted-order.name}";


    private final ObjectMapper objectMapper;
    private final OrderService orderService;

    @Autowired
    public ConsumerOrderImpl(ObjectMapper objectMapper, OrderService orderService) {
        this.objectMapper = objectMapper;
        this.orderService = orderService;
    }

    @Override
    @KafkaListener(topics = orderCreatedTopic)
    public void consumeMessageAboutOrderChecked(String message) throws JsonProcessingException {
        log.info("message consumed (order checked) {}", message);
        OrderDto orderDto = objectMapper.readValue(message, OrderDto.class);
        orderDto.setStatus(StatusRq.CREATED_ORDER.getStatus());
        UtilsClient.orderDtoMap.put(orderDto.getIdRequest(), orderDto);
        UtilsClient.statusRequest.put(orderDto.getIdRequest(), StatusRq.CREATED_ORDER);
        orderService.sendForProcessing(orderDto, ProducerTopic.PRODUCT);
    }

    @Override
    @KafkaListener(topics = productCheckedTopic)
    public void consumeMessageAboutProductChecked(String message) throws JsonProcessingException {
        log.info("message consumed (product checked) {}", message);
        OrderDto orderDto = objectMapper.readValue(message, OrderDto.class);
        orderDto.setStatus(StatusRq.WAITING_FOR_PAYMENT.getStatus());
        UtilsClient.orderDtoMap.put(orderDto.getIdRequest(), orderDto);
        UtilsClient.statusRequest.put(orderDto.getIdRequest(), StatusRq.WAITING_FOR_PAYMENT);
        System.out.println("DTO MAP TEST " + UtilsClient.orderDtoMap.get(orderDto.getIdRequest()));
        orderService.sendForProcessing(orderDto, ProducerTopic.WAITING_FOR_PAYMENT);
    }

    @Override
    @KafkaListener(topics = productFailCheckedTopic)
    public void consumeMessageAboutFailProductChecked(String message) throws JsonProcessingException {
        log.info("message consumed (fail product checked) {}", message);
        OrderDto orderDto = objectMapper.readValue(message, OrderDto.class);
        orderDto.setStatus(StatusRq.FAIL_CHECKED_PRODUCT.getStatus());
        UtilsClient.orderDtoMap.put(orderDto.getIdRequest(), orderDto);
        UtilsClient.statusRequest.put(orderDto.getIdRequest(), StatusRq.FAIL_CHECKED_PRODUCT);
        orderService.sendForProcessing(orderDto, ProducerTopic.DELETE_ORDER);
    }

    @Override
    @KafkaListener(topics = orderDeletedTopic)
    public void consumeMessageAboutDeletedOrder(String message) throws JsonProcessingException {
        log.info("message consumed (deleted order) {}", message);
        OrderDto orderDto = objectMapper.readValue(message, OrderDto.class);
        orderDto.setStatus(StatusRq.DELETED_ORDER.getStatus());
        UtilsClient.orderDtoMap.put(orderDto.getIdRequest(), orderDto);
        UtilsClient.statusRequest.put(orderDto.getIdRequest(), StatusRq.DELETED_ORDER);
    }
}
