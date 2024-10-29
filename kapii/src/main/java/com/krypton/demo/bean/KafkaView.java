package com.krypton.demo.bean;

import lombok.Data;

import java.util.List;

@Data
public class KafkaView {
private String clusterId;
private Integer brokerCount;
private List<Broker> brokers;
private Integer topicCount;
private List<KafkaTopic> kafkaTopics;
}
