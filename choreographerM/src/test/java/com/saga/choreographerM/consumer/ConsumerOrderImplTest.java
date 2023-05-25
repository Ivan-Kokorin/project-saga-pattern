package com.saga.choreographerM.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;

import com.saga.choreographerM.Utils.StatusRq;
import com.saga.choreographerM.Utils.UtilsClient;
import com.saga.choreographerM.model.OrderDto;
import com.saga.choreographerM.producer.ProducerOrderImpl;
import com.saga.choreographerM.producer.ProducerTopic;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.event.annotation.BeforeTestClass;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DirtiesContext
@EmbeddedKafka(partitions = 1, brokerProperties = { "listeners=PLAINTEXT://localhost:9092", "port=9092" })
class ConsumerOrderImplTest {
    @Autowired
    ApplicationContext context;
    @Autowired
    private ConsumerOrderImplWrap kafkaConsumer;

    @Autowired
    private ProducerOrderImpl kafkaProducer;

    @Value("${spring.kafka.test.topic}")
    private String topic;

    @Test
    void consumeMessageAboutOrderCheckedTest() {
        OrderDto data = createDataTest(StatusRq.START_PROCESSING_ORDER);
        boolean messageConsumed = false;
        try {
            kafkaProducer.sendMessage("t.product.create-order-test", data);
            messageConsumed = kafkaConsumer.getLatch().await(10, TimeUnit.SECONDS);
            Thread.sleep(100);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        assertTrue(messageConsumed);
        data.setStatus(StatusRq.CREATED_ORDER.getStatus());
        OrderDto orderFromMap = UtilsClient.orderDtoMap.get(data.getIdRequest());
        StatusRq statusRq = UtilsClient.statusRequest.get(orderFromMap.getIdRequest());
        assertThat(statusRq).isEqualTo(StatusRq.CREATED_ORDER);
        assertThat(orderFromMap).isEqualTo(data);
    }

    @Test
    void consumeMessageAboutProductCheckedTest() {
        OrderDto data = createDataTest(StatusRq.CREATED_ORDER);
        boolean messageConsumed = false;
        try {
            kafkaProducer.sendMessage("t.product.create-order-test", data);
            messageConsumed = kafkaConsumer.getLatch().await(10, TimeUnit.SECONDS);
            Thread.sleep(100);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        assertTrue(messageConsumed);
        data.setStatus(StatusRq.WAITING_FOR_PAYMENT.getStatus());
        OrderDto orderFromMap = UtilsClient.orderDtoMap.get(data.getIdRequest());
        System.out.println("11111111 " + UtilsClient.orderDtoMap);
        StatusRq statusRq = UtilsClient.statusRequest.get(orderFromMap.getIdRequest());
        assertThat(statusRq).isEqualTo(StatusRq.WAITING_FOR_PAYMENT);
        assertThat(orderFromMap).isEqualTo(data);
    }
    @Test
    void consumeMessageAboutFailProductCheckedTest() {
        OrderDto data = createDataTest(StatusRq.CHECKED_PRODUCT);
        boolean messageConsumed = false;
        try {
            kafkaProducer.sendMessage("t.product.create-order-test", data);
            messageConsumed = kafkaConsumer.getLatch().await(10, TimeUnit.SECONDS);
            Thread.sleep(100);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        assertTrue(messageConsumed);
        data.setStatus(StatusRq.FAIL_CHECKED_PRODUCT.getStatus());
        OrderDto orderFromMap = UtilsClient.orderDtoMap.get(data.getIdRequest());
        StatusRq statusRq = UtilsClient.statusRequest.get(orderFromMap.getIdRequest());
        assertThat(statusRq).isEqualTo(StatusRq.FAIL_CHECKED_PRODUCT);
        assertThat(orderFromMap).isEqualTo(data);
    }

    @Test
    void consumeMessageAboutDeletedOrderTest() {
        OrderDto data = createDataTest(StatusRq.FAIL_CHECKED_PRODUCT);
        boolean messageConsumed = false;
        try {
            kafkaProducer.sendMessage("t.product.create-order-test", data);
            messageConsumed = kafkaConsumer.getLatch().await(10, TimeUnit.SECONDS);
            Thread.sleep(100);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        assertTrue(messageConsumed);
        data.setStatus(StatusRq.DELETED_ORDER.getStatus());
        OrderDto orderFromMap = UtilsClient.orderDtoMap.get(data.getIdRequest());
        StatusRq statusRq = UtilsClient.statusRequest.get(orderFromMap.getIdRequest());
        assertThat(statusRq).isEqualTo(StatusRq.DELETED_ORDER);
        assertThat(orderFromMap).isEqualTo(data);
    }

    private OrderDto createDataTest(StatusRq status) {
        OrderDto data = new OrderDto();
        data.setId(12345L);
        data.setCreated(LocalDateTime.now());
        data.setIdRequest(UtilsClient.createIdRequest(data.getCreated()));
        data.setIdUser(121212L);
        data.setIdProduct("id-SSD-12345");
        data.setAmount(50);
        data.setStatus(status.getStatus());
        System.out.println(data);
        return data;
    }
}