package com.workflow;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HelloWorldControllerTest {

    @Test
    void testHelloWorld_ControllerCanBeInstantiated() {
        // Test that the controller can be created
        HelloWorldController controller = new HelloWorldController();
        assertNotNull(controller);
    }

    @Test
    void testHelloWorld_ReturnsCorrectMessage() {
        // Test the helloWorld method directly
        HelloWorldController controller = new HelloWorldController();
        String result = controller.helloWorld();
        assertEquals("Hello World", result);
    }

    @Test
    void testHelloWorld_ReturnsNonNullString() {
        HelloWorldController controller = new HelloWorldController();
        String result = controller.helloWorld();
        assertNotNull(result);
    }

    @Test
    void testHelloWorld_ReturnsNonEmptyString() {
        HelloWorldController controller = new HelloWorldController();
        String result = controller.helloWorld();
        assertFalse(result.isEmpty());
    }
}

