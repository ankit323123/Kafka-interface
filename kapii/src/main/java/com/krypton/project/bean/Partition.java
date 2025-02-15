package com.krypton.project.bean;

import lombok.Data;

@Data
public class Partition {
    private Integer id;
    private Integer leader;
    private Integer replica;
    private Integer isr;
}
