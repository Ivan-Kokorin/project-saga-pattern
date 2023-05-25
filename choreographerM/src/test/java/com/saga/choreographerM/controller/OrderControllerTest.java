package com.saga.choreographerM.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.saga.choreographerM.Utils.StatusRq;
import com.saga.choreographerM.Utils.UtilsClient;
import com.saga.choreographerM.model.OrderDto;
import com.saga.choreographerM.services.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class OrderControllerTest {
    @MockBean
    private OrderService orderService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    private OrderDto data;

    @BeforeEach
    public void createOrderDtoTest() {
        data = new OrderDto();
        data.setId(12345L);
        data.setIdUser(121212L);
        data.setIdProduct("id-SSD-12345");
        data.setAmount(50);
    }

    @Test
    void createOrderTest() throws Exception {
        sendTestRequest(StatusRq.WAITING_FOR_PAYMENT);
    }

    @Test
    void createOrderFailedTest() throws Exception {
        sendTestRequest(StatusRq.FAIL_CHECKED_PRODUCT);
    }

    private void sendTestRequest(StatusRq status) throws Exception {
        Mockito.when(orderService.sendForProcessing(Mockito.any(), Mockito.any())).thenReturn("message sent");
        OrderDto orderController = data;
        orderController.setCreated(LocalDateTime.now());
        orderController.setIdRequest(UtilsClient.createIdRequest(orderController.getCreated()));
        orderController.setStatus(StatusRq.START_PROCESSING_ORDER.getStatus());
        UtilsClient.statusRequest.put(orderController.getIdRequest(), status);
        Mockito.when(orderService.supplementNewDto(Mockito.any())).thenReturn(orderController);
        mockMvc.perform(
                        post("/order")
                                .content(objectMapper.writeValueAsString(data))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(content().string(status.getStatus()));
    }
}