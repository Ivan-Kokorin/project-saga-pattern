package com.saga.choreographerM.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.saga.choreographerM.model.OrderDto;

public interface Producer {
    public String sendMessage(String topic, OrderDto orderDto) throws JsonProcessingException;
}
