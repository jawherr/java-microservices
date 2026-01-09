package com.example.common.dto;

public class GreetingDto {
    private String name;
    private String message;

    public GreetingDto() {
    }

    public GreetingDto(String name, String message) {
        this.name = name;
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

