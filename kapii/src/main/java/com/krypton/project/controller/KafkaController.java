package com.krypton.project.controller;

import com.krypton.project.service.KafkaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/kafka")
public class KafkaController {

    @Autowired
    private KafkaService kafkaService;

    private String bootstrapServers;

    @PostMapping("/setConfig")
    public String setKafkaConfig(@RequestParam String clusterName,
                                 @RequestParam String bootstrapServers,
                                 @RequestParam String kafkaVersion,
                                 @RequestParam(required = false) Boolean enableZookeeper,
                                 @RequestParam(required = false) String zookeeperHost,
                                 @RequestParam(required = false) Integer zookeeperPort,
                                 @RequestParam(required = false) String zookeeperChroot) {
        this.bootstrapServers = bootstrapServers;
        return "redirect:/kafka/broker-info";
    }

    @GetMapping("/broker-info")
    public String getBrokerInfo(Model model) {
        model.addAttribute("brokerDetails", kafkaService.getKafkaBrokerInfo(bootstrapServers));
        return "broker-info";
    }
}
