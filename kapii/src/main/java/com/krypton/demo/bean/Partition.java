package com.krypton.demo.bean;

import lombok.Data;

@Data
public class Partition {
    private Integer id;
    private Integer leader;
    private Integer replica;
    private Integer isr;
}
