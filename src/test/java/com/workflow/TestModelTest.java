package com.workflow;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TestModelTest {

    @org.junit.jupiter.api.Test
    void testDefaultConstructor() {
        com.workflow.projet.model.Test test = new com.workflow.projet.model.Test();
        assertEquals(0, test.getId());
        assertNull(test.getText());
        assertNull(test.getDate());
    }

    @org.junit.jupiter.api.Test
    void testParameterizedConstructor() {
        com.workflow.projet.model.Test test = new com.workflow.projet.model.Test(1, "Test text", "2024-01-15");

        assertEquals(1, test.getId());
        assertEquals("Test text", test.getText());
        assertEquals("2024-01-15", test.getDate());
    }

    @org.junit.jupiter.api.Test
    void testSettersAndGetters() {
        com.workflow.projet.model.Test test = new com.workflow.projet.model.Test();

        test.setId(5);
        test.setText("New text");
        test.setDate("2024-02-20");

        assertEquals(5, test.getId());
        assertEquals("New text", test.getText());
        assertEquals("2024-02-20", test.getDate());
    }

    @org.junit.jupiter.api.Test
    void testSetDateWithSqlDate() {
        com.workflow.projet.model.Test test = new com.workflow.projet.model.Test();
        java.sql.Date sqlDate = java.sql.Date.valueOf("2024-03-10");

        test.setDate(sqlDate);

        assertEquals("2024-03-10", test.getDate());
    }

    @org.junit.jupiter.api.Test
    void testSetDateWithNullSqlDate() {
        com.workflow.projet.model.Test test = new com.workflow.projet.model.Test();

        test.setDate((java.sql.Date) null);

        assertNull(test.getDate());
    }

    @org.junit.jupiter.api.Test
    void testEquality() {
        com.workflow.projet.model.Test test1 = new com.workflow.projet.model.Test(1, "Text", "2024-01-01");
        com.workflow.projet.model.Test test2 = new com.workflow.projet.model.Test(1, "Text", "2024-01-01");

        assertEquals(test1.getId(), test2.getId());
        assertEquals(test1.getText(), test2.getText());
        assertEquals(test1.getDate(), test2.getDate());
    }
}

