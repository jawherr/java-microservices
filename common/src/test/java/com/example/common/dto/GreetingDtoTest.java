package com.example.common.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GreetingDtoTest {

    @Test
    void basicPojo() {
        GreetingDto dto = new GreetingDto("Alice", "Hello Alice");
        assertEquals("Alice", dto.getName());
        assertEquals("Hello Alice", dto.getMessage());
    }
}

