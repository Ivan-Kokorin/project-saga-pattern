package com.saga.orderM.service;

import com.saga.orderM.model.OrderDto;

public interface OrderService {
    public void persistOrder(OrderDto orderDto);
}
