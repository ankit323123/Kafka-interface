package com.krypton.project.service;

import java.util.Map;

public interface KafkaService {
    public Map<String, Object> getKafkaBrokerInfo(String bootstrapServers);
}
