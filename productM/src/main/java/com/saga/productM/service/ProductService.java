package com.saga.productM.service;

import com.saga.productM.model.OrderDto;

public interface ProductService {
    public boolean checkNumberOfProducts(OrderDto orderDto);
}
