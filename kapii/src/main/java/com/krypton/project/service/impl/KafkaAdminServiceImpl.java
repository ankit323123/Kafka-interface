package com.krypton.project.service.impl;

import com.krypton.project.bean.Broker;
import com.krypton.project.bean.KafkaTopic;
import com.krypton.project.bean.KafkaView;
import com.krypton.project.bean.Partition;
import com.krypton.project.service.KafkaAdminService;
import org.apache.kafka.clients.admin.*;
import org.apache.kafka.common.Node;
import org.apache.kafka.common.TopicPartitionInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ExecutionException;

@Service
public class KafkaAdminServiceImpl implements KafkaAdminService {
    @Autowired
    KafkaAdmin kafkaAdmin; //abstraction over admin api
    private Logger log = LoggerFactory.getLogger(this.getClass());


    public String createTopic(String topic, Integer replicationFactor, Integer partitions){
        NewTopic newTopic= new NewTopic(topic,partitions,replicationFactor.shortValue());
        kafkaAdmin.createOrModifyTopics(newTopic);
        return "topic created";
    }
    public String deleteTopic(String topicName) {
        try (AdminClient adminClient = AdminClient.create(kafkaAdmin.getConfigurationProperties())) {
            // Delete the topic
            adminClient.deleteTopics(Collections.singletonList(topicName)).all().get();
            log.info("Topic deleted successfully: " + topicName);
            return "Topic deleted successfully: " + topicName;
        } catch (ExecutionException | InterruptedException e) {
            log.error("Failed to delete topic: " + e.getMessage());
            Thread.currentThread().interrupt();
            return "Failed to delete topic: " + e.getMessage();
        }
    }

    public KafkaView displayKafkaInfo() throws ExecutionException, InterruptedException {
        try (AdminClient adminClient = AdminClient.create(kafkaAdmin.getConfigurationProperties())) {
            KafkaView kafkaView= new KafkaView();

            DescribeClusterResult clusterResult = adminClient.describeCluster();
            log.info("Cluster ID: " + clusterResult.clusterId().get());
            kafkaView.setClusterId(clusterResult.clusterId().get());
            List<Broker> brokerList = new ArrayList<>();
            List<KafkaTopic> topics =new ArrayList<>();

            log.info("Number of Brokers: " + clusterResult.nodes().get().size());
            kafkaView.setBrokerCount(clusterResult.nodes().get().size());
            for (Node broker : clusterResult.nodes().get()) {
                log.info("\tBroker ID: " + broker.id() + ", Host: " + broker.host() + ", Port: " + broker.port());
                Broker broker1= new Broker();
                broker1.setBrokerId(broker.id());
                broker1.setHost(broker.host());
                broker1.setPort(broker.port());
                brokerList.add(broker1);

            }
            kafkaView.setBrokers(brokerList);

            ListTopicsResult listTopicsResult = adminClient.listTopics();
            for (String topicName : listTopicsResult.names().get()) {
                log.info("\nTopic: " + topicName);
                KafkaTopic kafkaTopic=new KafkaTopic();
                kafkaTopic.setName(topicName);

                DescribeTopicsResult describeTopicsResult = adminClient.describeTopics(listTopicsResult.names().get());
                TopicDescription topicDescription = describeTopicsResult.values().get(topicName).get();
                List<Partition> partitions=new ArrayList<>();

                for (TopicPartitionInfo partitionInfo : topicDescription.partitions()) {
                    Partition partition =new Partition();
                    log.info("\tPartition: " + partitionInfo.partition());
                    log.info("\tLeader: " + (partitionInfo.leader()!=null ? partitionInfo.leader().id(): "NA"));
                    log.info("\tReplicas: " + partitionInfo.replicas().size());
                    log.info("\tIsr: " + partitionInfo.isr().size());
                    partition.setId(partitionInfo.partition());
                    partition.setLeader((partitionInfo.leader()!=null ? partitionInfo.leader().id(): -1));
                    partition.setReplica(partitionInfo.replicas().size());
                    partition.setIsr(partitionInfo.isr().size());
                    partitions.add(partition);


                }
                kafkaTopic.setPartitions(partitions);
                topics.add(kafkaTopic);

            }
            kafkaView.setKafkaTopics(topics);
            kafkaView.setTopicCount(topics.size());
            return kafkaView;
        }
    }
}
