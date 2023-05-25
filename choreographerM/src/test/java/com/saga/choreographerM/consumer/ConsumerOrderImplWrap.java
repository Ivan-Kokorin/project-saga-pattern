package com.saga.choreographerM.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.saga.choreographerM.model.OrderDto;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.context.ApplicationContext;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.test.annotation.DirtiesContext;

import java.util.concurrent.CountDownLatch;

@SpringBootTest
@DirtiesContext
@Component
@Data
public class ConsumerOrderImplWrap implements Consumer{
    private CountDownLatch latch = new CountDownLatch(1);
    ConsumerOrderImpl consumerOrderImpl;
    @Autowired
    ApplicationContext context;

    public ConsumerOrderImplWrap(ApplicationContext context) {
        this.consumerOrderImpl = context.getBean(ConsumerOrderImpl.class);
    }

    @Override
    @KafkaListener(topics = "t.product.create-order-test")
    public void consumeMessageAboutOrderChecked(String message) throws JsonProcessingException {
        consumerOrderImpl.consumeMessageAboutOrderChecked(message);
        latch.countDown();
    }

    @Override
    @KafkaListener(topics = "t.product.check-product-test")
    public void consumeMessageAboutProductChecked(String message) throws JsonProcessingException {
        consumerOrderImpl.consumeMessageAboutProductChecked(message);
        latch.countDown();
    }

    @Override
    @KafkaListener(topics = "t.product.fail-product-checked-test")
    public void consumeMessageAboutFailProductChecked(String message) throws JsonProcessingException {
        consumerOrderImpl.consumeMessageAboutFailProductChecked(message);
        latch.countDown();
    }

    @Override
    @KafkaListener(topics = "t.product.deleted-order-test")
    public void consumeMessageAboutDeletedOrder(String message) throws JsonProcessingException {
        consumerOrderImpl.consumeMessageAboutDeletedOrder(message);
        latch.countDown();
    }
    public void resetLatch() {
        latch = new CountDownLatch(1);
    }

}
