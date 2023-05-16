package com.saga.productM.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface Consumer {
    public void consumeMessageCheckProduct(String message) throws JsonProcessingException;
}
