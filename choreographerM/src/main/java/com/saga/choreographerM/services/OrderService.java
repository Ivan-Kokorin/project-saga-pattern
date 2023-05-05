package com.saga.choreographerM.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.saga.choreographerM.model.OrderDto;
import org.springframework.stereotype.Service;

@Service
public interface OrderService {
    public String createOrder(OrderDto orderDto) throws JsonProcessingException;
}
