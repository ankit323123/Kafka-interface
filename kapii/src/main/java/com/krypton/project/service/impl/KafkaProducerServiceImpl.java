package com.krypton.project.service.impl;

import com.krypton.project.service.CustomPartitioner;
import com.krypton.project.service.KafkaProducerService;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Properties;
import java.util.concurrent.CompletableFuture;


@Service
public class KafkaProducerServiceImpl implements KafkaProducerService {

    @Autowired
    KafkaTemplate kafkaTemplate; //abstraction over producer api

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public boolean sendMessage(String message, String topic){
        try {
            CompletableFuture<org.springframework.kafka.support.SendResult> kafkaResponse = kafkaTemplate.send(topic, message);
            kafkaResponse.whenComplete((data, ex)->{
                if(ex== null){
                    log.info("message sent successfully to topic {}, partition {}, offset {}",topic, data.getRecordMetadata().partition(), data.getRecordMetadata().offset());
                }
                else{
                    log.info("error sending message ");
                    Throwable error = new Throwable("error: " +ex.getMessage());
                }
            });
        }catch (Exception e){
            return false;
        }

        return true;
    }
    @Override
    public boolean sendMessageWithKey( String message , String key ,String topic){
        try {
            CompletableFuture<org.springframework.kafka.support.SendResult> kafkaResponse = kafkaTemplate.send(topic, key, message);
            kafkaResponse.whenComplete((data, ex)->{
                if(ex== null){
                    log.info("message sent successfully to topic {}, partition {}, offset {}",topic, data.getRecordMetadata().partition(), data.getRecordMetadata().offset());
                }
                else{
                    log.info("error sending message ");
                    Throwable error = new Throwable("error: " +ex.getMessage());
                }
            });
        }catch (Exception e){
            return false;
        }

        return true;
    }
    @Override
    public boolean sendMessageWithCustomPartitioner(String message , String key ,String topic){
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, CustomPartitioner.class.getName());

        KafkaProducer<String, String> producer = new KafkaProducer<>(props);

        ProducerRecord<String, String> record = new ProducerRecord<>(topic, key, message);

        // Send data - asynchronous
        producer.send(record, (RecordMetadata metadata, Exception e) -> {
            if (e == null) {
                log.info("Record sent" + " to partition " + metadata.partition()
                        + " with offset " + metadata.offset());
            } else {
                e.printStackTrace();
            }
        });

        producer.close();
        return true;
    }


}
