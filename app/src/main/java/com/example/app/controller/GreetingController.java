package com.example.app.controller;

import com.example.common.dto.GreetingDto;
import com.example.core.service.GreetingService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

    private final GreetingService greetingService;

    public GreetingController(GreetingService greetingService) {
        this.greetingService = greetingService;
    }

    @GetMapping("/api/greet")
    public GreetingDto greet(@RequestParam(required = false) String name) {
        String message = greetingService.greet(name);
        return new GreetingDto(name, message);
    }
}

