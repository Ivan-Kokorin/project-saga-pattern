package com.saga.productM.producer;

public enum ProducerTopic {
    CHECKED_PRODUCT("t.product.product-checked"),
    FAIL_CHECKED_PRODUCT("t.product.fail-product-checked"),
    PRODUCT_FREEZE("t.product.product-freezed");
    private String topic;

    ProducerTopic(String topic) {
        this.topic = topic;
    }

    public String getTopic() {
        return topic;
    }
}
