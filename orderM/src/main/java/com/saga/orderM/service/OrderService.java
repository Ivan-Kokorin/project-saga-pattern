package com.saga.orderM.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.saga.orderM.model.OrderDto;
import com.saga.orderM.producer.ProducerTopic;

public interface OrderService {
    public OrderDto persistOrder(OrderDto orderDto);

    public String sendForProcessing(OrderDto orderDto, ProducerTopic topic) throws JsonProcessingException;

}
