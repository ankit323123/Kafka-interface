package com.krypton.project.controller;

import org.springframework.stereotype.Controller;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

@Controller
public class ConnectionController {

    @GetMapping("/connection")
    public String kafkaConfig() {
        return "add-connection";
    }
    @PostMapping("/test-connection")
    @ResponseBody
    public String testConnection(@RequestParam String bootstrapServers) {
        Properties props = new Properties();
        props.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);

        try (AdminClient adminClient = AdminClient.create(props)) {
            adminClient.listTopics().names().get();  // Try fetching topic names to check connection
            return "Connection successful!";
        } catch (InterruptedException | ExecutionException e) {
            return "Failed to connect: " + e.getMessage();
        }
    }

    @PostMapping("/connect")
    public String connectToKafka(@RequestParam String clusterName,
                                 @RequestParam String bootstrapServers,
                                 @RequestParam String kafkaVersion,
                                 @RequestParam(required = false) Boolean enableZookeeper,
                                 @RequestParam(required = false) String zookeeperHost,
                                 @RequestParam(required = false) Integer zookeeperPort,
                                 @RequestParam(required = false) String zookeeperChroot,
                                 Model model) {

        model.addAttribute("message", "Successfully connected to Kafka cluster: " + clusterName);
        return "connect";
    }
}
