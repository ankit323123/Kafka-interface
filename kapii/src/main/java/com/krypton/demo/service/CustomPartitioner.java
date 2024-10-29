package com.krypton.demo.service;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;
import java.util.Map;
public class CustomPartitioner
        implements Partitioner {
    @Override
    public void configure(Map<String, ?> configs) {
    }
    @Override
    public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster) {
        int numPartitions = cluster.partitionCountForTopic(topic);
        if (key instanceof String) {
            String keyStr = (String) key;
            if (keyStr.length() % 2 == 0) {
                return 0;
            } else {
                return 1;
            }
        }
        return Math.abs((key == null ? 0 : key.hashCode()) % numPartitions);
    }
    @Override
    public void close() {
    }
}
