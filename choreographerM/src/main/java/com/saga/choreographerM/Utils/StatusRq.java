package com.saga.choreographerM.Utils;

public enum StatusRq {
    START_PROCESSING_ORDER,
    CREATED_ORDER,
    CHECKED_PRODUCT,
    FAIL_CHECKED_PRODUCT,
    DELETED_ORDER,
    WAITING_FOR_PAYMENT;
}
