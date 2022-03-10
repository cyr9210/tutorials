package com.example.demo;

import com.example.demo.model.Review;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@SpringBootTest
class DemoApplicationTests {

    @PersistenceContext
    protected EntityManager entityManager;

    @Test
    void contextLoads() {
        new Review("content");
    }

}
