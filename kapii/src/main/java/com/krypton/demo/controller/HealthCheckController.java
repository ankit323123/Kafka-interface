package com.krypton.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/demo/v1")
public class HealthCheckController {

    @GetMapping("/health-check")
    public String healthCheck(){
        return "service online";
    }

}
