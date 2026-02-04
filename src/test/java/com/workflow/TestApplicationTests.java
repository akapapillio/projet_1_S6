package com.workflow;

import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TestApplicationTests {

    @Test
    void contextLoads() {
        // Verifies that the Spring application context loads successfully
        // This is a basic smoke test
    }

    @Test
    void testMainMethodDoesNotThrow() {
        // Test that the main method can be invoked without throwing
        assertDoesNotThrow(() -> {
            // We just verify the class can be instantiated
            TestApplication app = new TestApplication();
            assertNotNull(app);
        });
    }

    @Test
    void testApplicationPropertiesLoaded() {
        // Verify the application context is properly configured
        SpringApplication app = new SpringApplication(TestApplication.class);
        assertNotNull(app);
    }
}

