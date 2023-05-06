package com.saga.orderM.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.saga.orderM.model.OrderDto;

public interface Producer {
    public String sendMessage(OrderDto orderDto, ProducerTopic topic) throws JsonProcessingException;
}
