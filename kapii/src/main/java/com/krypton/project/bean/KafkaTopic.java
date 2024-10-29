package com.krypton.project.bean;

import lombok.Data;

import java.util.List;

@Data
public class KafkaTopic {
    String name;
    List<Partition> partitions;
}
