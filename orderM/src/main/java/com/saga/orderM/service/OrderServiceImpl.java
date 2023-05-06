package com.saga.orderM.service;

import com.saga.orderM.model.Order;
import com.saga.orderM.model.OrderDto;
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

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, ModelMapper modelMapper) {
        this.orderRepository = orderRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void persistOrder(OrderDto orderDto) {
        Order order = modelMapper.map(orderDto, Order.class);
        Order persistedOrder = orderRepository.save(order);
        log.info("order persisted {}", persistedOrder);
    }
}
