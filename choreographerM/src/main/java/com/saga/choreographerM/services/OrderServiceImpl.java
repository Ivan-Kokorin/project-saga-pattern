package com.saga.choreographerM.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.saga.choreographerM.Utils.StatusRq;
import com.saga.choreographerM.Utils.UtilsClient;
import com.saga.choreographerM.model.OrderDto;
import com.saga.choreographerM.producer.Producer;
import com.saga.choreographerM.producer.ProducerTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class OrderServiceImpl implements OrderService {
    private final Producer producer;

    @Autowired
    public OrderServiceImpl(Producer producer) {
        this.producer = producer;
    }

    @Override
    public OrderDto supplementNewDto(OrderDto orderDto) {
        orderDto.setCreated(LocalDateTime.now());
        orderDto.setIdRequest(UtilsClient.createIdRequest(orderDto.getCreated()));
        orderDto.setStatus(StatusRq.START_PROCESSING_ORDER.getStatus());
        UtilsClient.statusRequest.put(orderDto.getIdRequest(), StatusRq.START_PROCESSING_ORDER);
        return orderDto;
    }

    @Override
    public String sendForProcessing(OrderDto orderDto, ProducerTopic topic) throws JsonProcessingException {
        return producer.sendMessage(topic.getTopic(), orderDto);
    }
}
