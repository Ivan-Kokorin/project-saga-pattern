package com.saga.choreographerM.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.saga.choreographerM.model.OrderDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ProducerOrderImpl implements Producer{
    private final ObjectMapper objectMapper;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    public ProducerOrderImpl(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    @Override
    public String sendMessage(String topic, OrderDto orderDto) throws JsonProcessingException {
        String orderAsMessage = objectMapper.writeValueAsString(orderDto);
        kafkaTemplate.send(topic, orderAsMessage);
        log.info("order produced {}", orderAsMessage);
        return "message sent";
    }
}
