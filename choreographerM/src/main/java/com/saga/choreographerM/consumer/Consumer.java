package com.saga.choreographerM.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface Consumer {
    public void consumeMessageAboutOrderChecked(String message) throws JsonProcessingException;
    public void consumeMessageAboutProductChecked(String message) throws JsonProcessingException;
}
