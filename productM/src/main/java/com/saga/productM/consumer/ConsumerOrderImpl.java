package com.saga.productM.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.saga.productM.Utils.StatusRq;
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
    private static final String checkProductTopic = "${spring.topic-check-product.name}";
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
    @KafkaListener(topics = checkProductTopic)
    public void consumeMessageCheckProduct(String message) throws JsonProcessingException {
        log.info("message consumed (check product) {}", message);

        OrderDto orderDto = objectMapper.readValue(message, OrderDto.class);
        if (productService.checkNumberOfProducts(orderDto)) {
            orderDto.setStatus(StatusRq.CHECKED_PRODUCT.getStatus());
            orderService.persistOrder(orderDto);
            orderService.sendForProcessing(orderDto, ProducerTopic.CHECKED_PRODUCT);
        } else {
            orderDto.setStatus(StatusRq.FAIL_CHECKED_PRODUCT.getStatus());
            orderService.persistOrder(orderDto);
            orderService.sendForProcessing(orderDto, ProducerTopic.FAIL_CHECKED_PRODUCT);
        }

    }
}
