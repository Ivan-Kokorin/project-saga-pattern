package com.saga.orderM.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface Consumer {
    public void consumeCreateOrderMessage(String message) throws JsonProcessingException;
    public void consumeDeleteOrderMessage(String message) throws JsonProcessingException;
    public void consumeMessageWaitingForPayment(String message) throws JsonProcessingException;
}
