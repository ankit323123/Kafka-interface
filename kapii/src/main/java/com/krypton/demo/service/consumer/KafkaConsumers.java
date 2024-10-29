package com.krypton.demo.service.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

@Service
public class KafkaConsumers {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @KafkaListener(topics = {"letscreatenewtopic"}, groupId = "group1") // abstraction over consumer api
    public void consumeMessages(ConsumerRecord consumerRecord){
        log.info("consumed message for topic: {}, message: {}",consumerRecord.topic(),consumerRecord.value());
    }
    @KafkaListener(topics = {"letscreatenewtopic"}, groupId = "group1")
    public void consumeMessages2(ConsumerRecord consumerRecord){
        log.info("consumed message2 for topic: {}, message: {}",consumerRecord.topic(),consumerRecord.value());
    }

}

