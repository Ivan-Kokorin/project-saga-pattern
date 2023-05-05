package com.saga.choreographerM;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService{
    private final Producer producer;

    @Autowired
    public OrderServiceImpl(Producer producer) {
        this.producer = producer;
    }
    @Override
    public String createOrder(OrderDto orderDto) throws JsonProcessingException {
        return producer.sendMessage(orderDto);
    }
}
