package com.saga.productM.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.saga.productM.model.Order;
import com.saga.productM.model.OrderDto;
import com.saga.productM.producer.Producer;
import com.saga.productM.producer.ProducerTopic;
import com.saga.productM.repository.OrderRepository;
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
        log.info("order persisted {}", persistedOrder);
        return persistedOrderDto;
    }

    @Override
    public String sendForProcessing(OrderDto orderDto, ProducerTopic topic) throws JsonProcessingException {
        return producer.sendMessage(orderDto, topic);
    }
}
