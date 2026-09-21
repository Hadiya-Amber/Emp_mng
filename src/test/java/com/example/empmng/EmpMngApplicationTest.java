package com.example.empmng;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

@SpringBootTest(classes = com.example.empmng.EmpMngApplication.class)
@ActiveProfiles("test")
public class EmpMngApplicationTest {

    @Autowired
    private ApplicationContext context;

    @Test
    public void contextLoads() {
        Assertions.assertNotNull(context);
    }
}
