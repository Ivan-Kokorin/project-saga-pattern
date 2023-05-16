package com.saga.orderM.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.saga.orderM.model.Order;
import com.saga.orderM.model.OrderDto;
import com.saga.orderM.producer.Producer;
import com.saga.orderM.producer.ProducerTopic;
import com.saga.orderM.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;

    private final Producer producer;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, ModelMapper modelMapper, Producer producer) {
        this.orderRepository = orderRepository;
        this.modelMapper = modelMapper;
        this.producer = producer;
    }

    @Override
    public OrderDto persistOrder(OrderDto orderDto) {
        Order order = modelMapper.map(orderDto, Order.class);
        Order persistedOrder = orderRepository.save(order);
        OrderDto persistedOrderDto = modelMapper.map(persistedOrder, OrderDto.class);
        persistedOrderDto.setIdRequest(orderDto.getIdRequest());
        log.info("order persisted {}", persistedOrder);
        return persistedOrderDto;
    }

    @Override
    public String sendForProcessing(OrderDto orderDto, ProducerTopic topic) throws JsonProcessingException {
        return producer.sendMessage(orderDto, topic);
    }
}
