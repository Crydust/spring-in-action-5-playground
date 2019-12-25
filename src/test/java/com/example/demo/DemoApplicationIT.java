package com.example.demo;

import io.hypersistence.optimizer.HypersistenceOptimizer;
import io.hypersistence.optimizer.core.config.JpaConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

@SpringBootTest
public class DemoApplicationIT {
    @PersistenceUnit
    private EntityManagerFactory entityManagerFactory;

    @BeforeEach
    public void init() {
        new HypersistenceOptimizer(
                new JpaConfig(entityManagerFactory)
        ).init();
    }

    @Test
    public void contextLoads() {
    }

}
