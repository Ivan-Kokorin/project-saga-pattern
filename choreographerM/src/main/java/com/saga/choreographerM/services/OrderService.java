package com.saga.choreographerM.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.saga.choreographerM.model.OrderDto;
import com.saga.choreographerM.producer.ProducerTopic;
import org.springframework.stereotype.Service;

@Service
public interface OrderService {
    public String sendForProcessing(OrderDto orderDto, ProducerTopic topic) throws JsonProcessingException;
}
