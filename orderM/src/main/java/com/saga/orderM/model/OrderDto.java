package com.saga.orderM.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderDto {
    private Long id;
    private String idRequest;
    private Long idUser;
    private String idProduct;
    private Integer amount;
    private String status;
    private LocalDateTime created;

    @Override
    public String toString() {
        return "OrderDto{" +
                "id=" + id +
                ", idRequest='" + idRequest + '\'' +
                ", idUser=" + idUser +
                ", nameProduct='" + idProduct + '\'' +
                ", amount=" + amount +
                ", status='" + status + '\'' +
                ", created=" + created +
                '}';
    }
}
