package com.saga.orderM.producer;

public enum ProducerTopic {
    CHECKED_ORDER("t.product.order-created"),
    FINAL_ORDER_REGISTRATION("t.product.final-order"),
    FAIL_CHECKED_ORDER("t.product.fail-order-created"),
    DELETED_ORDER("t.product.deleted-order"),
    FAIL_FINAL_ORDER_REGISTRATION("t.product.fail-final-order");
    private String topic;

    ProducerTopic(String topic) {
        this.topic = topic;
    }

    public String getTopic() {
        return topic;
    }
}
