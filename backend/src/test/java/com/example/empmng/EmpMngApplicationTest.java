package com.example.empmng;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class EmpMngApplicationTest {

    @Test
    void contextLoads() {
        // If the context fails to load, this test will fail.
    }

    @LocalServerPort
    int port;

    @Autowired
    TestRestTemplate restTemplate;

    @Test
    void healthEndpointUp() {
        var resp = restTemplate.getForEntity("http://localhost:" + port + "/actuator/health", java.util.Map.class);
        assertThat(resp.getStatusCodeValue()).isEqualTo(200);
        assertThat(resp.getBody()).containsEntry("status", "UP");
    }
}
