package com.saga.productM.producer;

public enum ProducerTopic {
    PRODUCT_CHECKED("t.product.product-checked"),
    FAIL_PRODUCT_CHECKED("t.product.fail-product-checked"),
    PRODUCT_FREEZE("t.product.product-freezed");
    private String topic;

    ProducerTopic(String topic) {
        this.topic = topic;
    }

    public String getTopic() {
        return topic;
    }
}
