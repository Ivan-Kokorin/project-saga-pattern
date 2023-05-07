package com.saga.productM.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface Consumer {
    public void consumeMessage(String message) throws JsonProcessingException;
}
