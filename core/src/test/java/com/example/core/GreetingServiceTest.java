package com.example.core;

import com.example.core.service.GreetingService;
import com.example.core.service.impl.GreetingServiceImpl;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GreetingServiceTest {

    @Test
    void greetWithName() {
        GreetingService svc = new GreetingServiceImpl();
        assertEquals("Hello, Alice", svc.greet("Alice"));
    }

    @Test
    void greetWithNull() {
        GreetingService svc = new GreetingServiceImpl();
        assertEquals("Hello, World", svc.greet(null));
    }
}

