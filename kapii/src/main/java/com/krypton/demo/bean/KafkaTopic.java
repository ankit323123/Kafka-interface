package com.krypton.demo.bean;

import lombok.Data;

import java.util.List;

@Data
public class KafkaTopic {
    String name;
    List<Partition> partitions;
}
