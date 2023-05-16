package com.saga.productM.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "product")
public class Product {
    @Id
    private String id;
    @Column
    private String name;
    @Column
    private String description;
    @Column
    private int amount;
    @Column
    private int price;
}
