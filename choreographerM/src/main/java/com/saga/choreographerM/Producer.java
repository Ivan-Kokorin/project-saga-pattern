package com.saga.choreographerM;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface Producer {
    public String sendMessage(OrderDto orderDto) throws JsonProcessingException;
}
