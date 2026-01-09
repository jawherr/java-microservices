package com.example.app;

import com.example.common.dto.GreetingDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GreetingControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void greetEndpoint() {
        GreetingDto resp = this.restTemplate.getForObject("http://localhost:" + port + "/api/greet?name=Bob", GreetingDto.class);
        assertThat(resp).isNotNull();
        assertThat(resp.getMessage()).isEqualTo("Hello, Bob");
    }
}

