package com.krypton.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class KappiDashboardController {
    @GetMapping("/")
    public String dashboard() {
        return "dashboard";
    }
}
