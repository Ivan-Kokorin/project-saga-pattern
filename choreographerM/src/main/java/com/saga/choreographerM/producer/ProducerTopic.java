package com.saga.choreographerM.producer;

public enum ProducerTopic {
    ORDER ("t.product.create-order"),
    PRODUCT ("t.product.check-product"),
    WAITING_FOR_PAYMENT ("t.product.waiting-for-payment"),
    DELETE_ORDER("t.product.delete-order"),
    START_OF_PAYMENT("t.payment.start");

    private String topic;

    ProducerTopic(String topic) {
        this.topic = topic;
    }

    public String getTopic() {
        return topic;
    }
}
