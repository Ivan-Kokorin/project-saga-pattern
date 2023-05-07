package com.saga.productM.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderDto {
    private Long id;
    private Long idUser;
    private String nameProduct;
    private Integer amount;
    private String status;
    private LocalDateTime created;

    @Override
    public String toString() {
        return "OrderDto{" +
                "idUser=" + idUser +
                ", nameProduct='" + nameProduct + '\'' +
                ", amount=" + amount +
                ", status='" + status + '\'' +
                ", created=" + created +
                '}';
    }
}
