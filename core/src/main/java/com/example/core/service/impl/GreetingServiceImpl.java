package com.example.core.service.impl;

import com.example.core.service.GreetingService;
import org.springframework.stereotype.Service;

@Service
public class GreetingServiceImpl implements GreetingService {

    @Override
    public String greet(String name) {
        if (name == null || name.isBlank()) {
            return "Hello, World";
        }
        return "Hello, " + name.trim();
    }
}

