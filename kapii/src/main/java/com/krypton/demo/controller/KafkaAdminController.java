package com.krypton.demo.controller;

import com.krypton.demo.bean.KafkaView;
import com.krypton.demo.service.KafkaAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/demo/v1")
public class KafkaAdminController {

    @Autowired
    private KafkaAdminService kafkaAdminService;

    @GetMapping("/view")
    public KafkaView kakaView() throws ExecutionException, InterruptedException {
        return kafkaAdminService.displayKafkaInfo();

    }

    @PostMapping("/create/topic/{topic}")
    public String addTopic(@RequestParam(value = "replicationFactor",defaultValue = "1") Integer replicationFactor, @RequestParam(value = "partitions",defaultValue = "1") Integer partitions, @PathVariable String topic){
        return kafkaAdminService.createTopic(topic,replicationFactor,partitions);

    }
    @DeleteMapping("/delete/topic/{topic}")
    public String deleteTopic(@PathVariable String topic){
        return kafkaAdminService.deleteTopic(topic);

    }


}
