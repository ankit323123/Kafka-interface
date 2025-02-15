package com.krypton.project.service;

import com.krypton.project.bean.KafkaView;

import java.util.concurrent.ExecutionException;

public interface KafkaAdminService {
    String createTopic(String topic, Integer replicationFactor, Integer partitions);
    KafkaView displayKafkaInfo()throws ExecutionException, InterruptedException;
    String deleteTopic(String topicName);

}
