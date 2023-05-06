package com.saga.choreographerM.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.saga.choreographerM.model.OrderDto;
import com.saga.choreographerM.producer.Producer;
import com.saga.choreographerM.producer.ProducerTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {
    private final Producer producer;

    @Autowired
    public OrderServiceImpl(Producer producer) {
        this.producer = producer;
    }

    @Override
    public String sendForProcessing(OrderDto orderDto, ProducerTopic topic) throws JsonProcessingException {
        return producer.sendMessage(orderDto, topic);
    }
}
