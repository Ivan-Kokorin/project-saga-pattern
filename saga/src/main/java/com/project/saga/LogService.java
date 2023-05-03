package com.project.saga;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface LogService {
    public Log save(Log productFinishedDto);
    public void saveAll(List<Log> products);
    public boolean isExist(Long id);
    public List<Log> getAllProduct();
    public Log getProductById(Long id);
}
