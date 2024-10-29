package com.krypton.demo.service;

public interface KafkaProducerService {

    boolean sendMessage( String message , String topic);
    boolean sendMessageWithKey( String message , String key ,String topic);
    boolean sendMessageWithCustomPartitioner(String message , String key ,String topic);
}
