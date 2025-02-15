package com.krypton.project.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KappiDashboardController {
    @GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard";
    }
}
