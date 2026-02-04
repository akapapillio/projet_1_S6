package com.workflow;

import com.workflow.projet.config.DatabaseConnection;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseConnectionTest {

    @BeforeEach
    void setUp() {
        // Reset the connection before each test
        try {
            java.lang.reflect.Field connectionField = DatabaseConnection.class.getDeclaredField("connection");
            connectionField.setAccessible(true);
            Connection existingConnection = (Connection) connectionField.get(null);
            if (existingConnection != null && !existingConnection.isClosed()) {
                existingConnection.close();
            }
            connectionField.set(null, null);
        } catch (Exception e) {
            // Ignore - field may not exist or other issues
        }
    }

    @AfterEach
    void tearDown() {
        // Close connection after each test
        try {
            DatabaseConnection.closeConnection();
        } catch (Exception e) {
            // Ignore
        }
    }

    @Test
    void testGetConnection_SingletonBehavior() throws SQLException {
        Connection conn1 = DatabaseConnection.getConnection();
        Connection conn2 = DatabaseConnection.getConnection();

        assertNotNull(conn1);
        assertNotNull(conn2);
        // Same connection instance should be returned
        assertSame(conn1, conn2);
    }

    @Test
    void testGetConnection_URLIsCorrect() {
        // This test verifies the connection configuration without actually connecting
        // It tests the static final values through reflection
        try {
            java.lang.reflect.Field urlField = DatabaseConnection.class.getDeclaredField("URL");
            urlField.setAccessible(true);
            String url = (String) urlField.get(null);

            assertNotNull(url);
            assertTrue(url.contains("jdbc:postgresql://localhost:5433/projet_1_s6"));
        } catch (Exception e) {
            fail("Unable to verify URL through reflection: " + e.getMessage());
        }
    }

    @Test
    void testGetConnection_UserIsCorrect() {
        try {
            java.lang.reflect.Field userField = DatabaseConnection.class.getDeclaredField("USER");
            userField.setAccessible(true);
            String user = (String) userField.get(null);

            assertNotNull(user);
            assertEquals("projet_1_s6", user);
        } catch (Exception e) {
            fail("Unable to verify USER through reflection: " + e.getMessage());
        }
    }

    @Test
    void testCloseConnection_Success() throws SQLException {
        Connection conn = DatabaseConnection.getConnection();
        assertNotNull(conn);

        // Close should not throw an exception
        assertDoesNotThrow(() -> DatabaseConnection.closeConnection());
    }

    @Test
    void testCloseConnection_CanReconnectAfterClose() throws SQLException {
        Connection conn1 = DatabaseConnection.getConnection();
        DatabaseConnection.closeConnection();

        // After closing, getting a new connection should work
        Connection conn2 = DatabaseConnection.getConnection();
        assertNotNull(conn2);
        // Note: Due to singleton pattern, this might be a new connection
    }

    @Test
    void testGetConnection_AfterCloseReestablishes() throws SQLException {
        Connection conn1 = DatabaseConnection.getConnection();
        DatabaseConnection.closeConnection();

        Connection conn2 = DatabaseConnection.getConnection();

        assertNotNull(conn2);
        assertNotNull(conn1);
    }

    @Test
    void testDriverRegistration() {
        // Test that the PostgreSQL driver class can be loaded
        assertDoesNotThrow(() -> Class.forName("org.postgresql.Driver"));
    }
}

