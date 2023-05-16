package com.saga.orderM.consumer;

public enum StatusOrder {
    ERROR_CHECKED_PRODUCT("Недостаточное количество выбранного товара");

    private String status;

    StatusOrder(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
