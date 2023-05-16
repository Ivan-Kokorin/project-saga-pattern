package com.saga.productM.service;

import com.saga.productM.model.OrderDto;
import com.saga.productM.model.Product;
import com.saga.productM.producer.Producer;
import com.saga.productM.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class ProductServiceImpl implements ProductService{
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;
    private final Producer producer;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, ModelMapper modelMapper, Producer producer) {
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
        this.producer = producer;
    }

    @Override
    public boolean checkNumberOfProducts(OrderDto orderDto) {
        Product product = productRepository.findById(orderDto.getIdProduct()).orElseThrow();
        if (product.getAmount() >= orderDto.getAmount()) {
            return true;
        }
        return false;
    }
}
