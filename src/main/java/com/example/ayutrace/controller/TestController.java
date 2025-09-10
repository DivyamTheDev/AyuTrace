package com.example.ayutrace.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/test")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5173"})
public class TestController {

    @GetMapping("/health")
    public Map<String, Object> health() {
        Map<String, Object> health = new HashMap<>();
        health.put("status", "UP");
        health.put("service", "AyuTrace Herb Traceability Backend");
        health.put("timestamp", System.currentTimeMillis());
        return health;
    }

    @GetMapping("/info")
    public Map<String, Object> info() {
        Map<String, Object> info = new HashMap<>();
        info.put("application", "AyuTrace");
        info.put("version", "1.0.0");
        info.put("description", "Herb Traceability and Provenance System");
        info.put("backend", "Spring Boot");
        return info;
    }
}
