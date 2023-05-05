package com.saga.choreographerM;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.kafka.clients.producer.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public interface OrderService {
    public String createOrder(OrderDto orderDto) throws JsonProcessingException;
}
