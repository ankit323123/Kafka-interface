package com.krypton.project.controller;

import com.krypton.project.bean.KafkaView;
import com.krypton.project.service.KafkaAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/kapii/v1")
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
