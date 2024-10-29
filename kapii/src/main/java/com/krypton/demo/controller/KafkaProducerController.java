package com.krypton.demo.controller;

import com.krypton.demo.service.KafkaProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/demo/v1")
public class KafkaProducerController {

    @Autowired
    KafkaProducerService kafkaProducerService;


    @PostMapping("/sendMessage/{message}/topic/{topic}")
    public ResponseEntity<?> sendMessage(@PathVariable("message") String message, @PathVariable("topic") String topic){
        if( kafkaProducerService.sendMessage(message,topic))
            return ResponseEntity.ok("Message sent");
        return ResponseEntity.ofNullable("error");
    }
    @PostMapping("/sendMessage/{message}/key/{key}/topic/{topic}")
    public ResponseEntity<?> sendMessageWithKey(@PathVariable("message") String message, @PathVariable("topic") String topic, @PathVariable("key") String key){
        if( kafkaProducerService.sendMessageWithKey(message,key,topic))
            return ResponseEntity.ok("Message sent");
        return ResponseEntity.ofNullable("error");
    }
    @PostMapping("/sendMessage/{message}/key/{key}/topic/{topic}/partitioner")
    public ResponseEntity<?> sendMessageWithPartitioner(@PathVariable("message") String message, @PathVariable("topic") String topic, @PathVariable("key") String key){
        if( kafkaProducerService.sendMessageWithCustomPartitioner(message,key,topic))
            return ResponseEntity.ok("Message sent");
        return ResponseEntity.ofNullable("error");
    }
}
