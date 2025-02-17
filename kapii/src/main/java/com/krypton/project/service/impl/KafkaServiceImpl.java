package com.krypton.project.service.impl;

import com.krypton.project.service.KafkaService;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.DescribeClusterResult;
import org.apache.kafka.clients.admin.ListTopicsResult;
import org.apache.kafka.clients.admin.TopicDescription;
import org.apache.kafka.common.Node;
import org.springframework.stereotype.Service;

import java.util.*;
@Service
public class KafkaServiceImpl implements KafkaService {
    public Map<String, Object> getKafkaBrokerInfo(String bootstrapServers) {
        Map<String, Object> brokerInfo = new HashMap<>();

        Properties props = new Properties();
        props.put("bootstrap.servers", bootstrapServers);

        try (AdminClient adminClient = AdminClient.create(props)) {
            // Get Cluster Information
            DescribeClusterResult clusterResult = adminClient.describeCluster();
            Collection<Node> nodes = clusterResult.nodes().get();
            brokerInfo.put("brokers", nodes);

            // Get Topics Information
            ListTopicsResult topicsResult = adminClient.listTopics();
            Set<String> topicNames = topicsResult.names().get();
            Map<String, TopicDescription> topicDescriptions = adminClient.describeTopics(topicNames).all().get();

            brokerInfo.put("topics", topicDescriptions);
        } catch (Exception e) {
            brokerInfo.put("error", "Failed to fetch Kafka details: " + e.getMessage());
        }
        return brokerInfo;
    }
}

