package com.saga.orderM.Utils;

public enum StatusRq {
    START_PROCESSING_ORDER("accepted for processing"),
    CREATED_ORDER("the order has been created"),
    CHECKED_PRODUCT("checked product"),
    FAIL_CHECKED_PRODUCT("error when checking the amount of products"),
    DELETED_ORDER("deleted order"),
    WAITING_FOR_PAYMENT("Waiting for payment...");

    private String status;

    StatusRq(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
