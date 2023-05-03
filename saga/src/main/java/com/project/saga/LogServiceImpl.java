package com.project.saga;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class LogServiceImpl implements LogService{
    @Autowired
    LogRepository logRepository;
    @Autowired
    MappingUtils mappingUtils;
    @Override
    public Log save(Log log) {
        return logRepository.save(log);
    }

    @Override
    public void saveAll(List<Log> logs) {
        logRepository.saveAll(logs);
    }

    @Override
    public boolean isExist(Long id) {
        return logRepository.existsById(id);
    }

    @Override
    public List<Log> getAllProduct() {
        return logRepository.findAll();
    }

    @Override
    public Log getProductById(Long id) {
        return logRepository.findById(id).orElse(new Log());
    }
}
