package com.example.empmng;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = com.example.empmng.EmpMngApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class EmpMngApplicationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void contextLoadsWithTestProfile() {
        // If the application context fails to start (for example because it tries to connect to an external Postgres), this test will fail.
        assertNotNull(restTemplate, "TestRestTemplate should be available when the Spring context loads with the 'test' profile");
    }

    @Test
    public void actuatorHealthEndpointReturnsUp() {
        ResponseEntity<String> resp = restTemplate.getForEntity("/actuator/health", String.class);
        assertEquals(HttpStatus.OK, resp.getStatusCode(), "Actuator health endpoint should return 200 when the app runs with 'test' profile");
        assertNotNull(resp.getBody(), "Actuator health response should have a body");
        // Expect JSON containing a status field with value UP, e.g. {"status":"UP"}
        assertTrue(resp.getBody().contains("\"status\":\"UP\""), "Actuator health JSON should contain 'status':'UP'");
    }
}
