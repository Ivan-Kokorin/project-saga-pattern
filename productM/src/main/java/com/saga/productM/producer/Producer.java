package com.saga.productM.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.saga.productM.model.OrderDto;

public interface Producer {
    public String sendMessage(OrderDto orderDto, ProducerTopic topic) throws JsonProcessingException;
}
