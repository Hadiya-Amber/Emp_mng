package com.example.empmng;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main entrypoint for the Employee Management Spring Boot application.
 */
@SpringBootApplication
public class EmpMngApplication {
    /**
     * Application main method - starts Spring Boot.
     * @param args command line arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(EmpMngApplication.class, args);
    }
}
