package com.saga.productM.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.saga.productM.model.OrderDto;
import com.saga.productM.producer.ProducerTopic;


public interface OrderService {
    public OrderDto persistOrder(OrderDto orderDto);

    public String sendForProcessing(OrderDto orderDto, ProducerTopic topic) throws JsonProcessingException;

}
