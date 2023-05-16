package com.saga.productM.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.saga.productM.model.OrderDto;
import com.saga.productM.producer.ProducerTopic;
import com.saga.productM.service.OrderService;
import com.saga.productM.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ConsumerOrderImpl implements Consumer {
    private static final String orderTopic = "${spring.topic-check-product.name}";
    private final ObjectMapper objectMapper;
    private final OrderService orderService;
    private final ProductService productService;

    @Autowired
    public ConsumerOrderImpl(ObjectMapper objectMapper, OrderService orderService, ProductService productService) {
        this.objectMapper = objectMapper;
        this.orderService = orderService;
        this.productService = productService;
    }

    @Override
    @KafkaListener(topics = orderTopic)
    public void consumeMessage(String message) throws JsonProcessingException {
        log.info("message consumed {}", message);

        OrderDto orderDto = objectMapper.readValue(message, OrderDto.class);
        if (productService.checkNumberOfProducts(orderDto)) {
            //todo change status
            orderService.sendForProcessing(orderDto, ProducerTopic.PRODUCT_CHECKED);
        } else {
            orderService.sendForProcessing(orderDto, ProducerTopic.FAIL_PRODUCT_CHECKED);
        }

    }
}
