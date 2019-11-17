package com.quasar.backend.service;

public interface MqService {

    boolean sendTask(String topic, String tags, String content);

    void sendTaskAsync(String topic, String tags, String content);
}
