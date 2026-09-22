package com.example.empmng;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Application entry point for the Employee Management service.
 *
 * <p>This class boots the Spring application context for the backend service.
 */
@SpringBootApplication
public class EmpMngApplication {

    /**
     * Start the Spring Boot application.
     *
     * @param args runtime arguments forwarded to SpringApplication
     */
    public static void main(String[] args) {
        SpringApplication.run(EmpMngApplication.class, args);
    }
}
