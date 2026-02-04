package com.workflow;

import com.workflow.projet.repository.TestRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class TestRepositoryTest {

    private TestRepository testRepository;
    private java.sql.Connection mockConnection;
    private PreparedStatement mockPreparedStatement;
    private ResultSet mockResultSet;

    @BeforeEach
    void setUp() {
        testRepository = new TestRepository();
        mockConnection = mock(java.sql.Connection.class);
        mockPreparedStatement = mock(PreparedStatement.class);
        mockResultSet = mock(ResultSet.class);
    }

    @Test
    void findAll_Success() throws Exception {
        // Setup the mock connection first
        org.mockito.MockitoAnnotations.openMocks(this);
        
        // Use reflection to set the mock connection
        setPrivateStaticField(
            com.workflow.projet.config.DatabaseConnection.class, 
            "connection", 
            mockConnection
        );
        
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);

        // Configure result set for multiple rows
        when(mockResultSet.next()).thenReturn(true, true, false);
        when(mockResultSet.getInt("id")).thenReturn(1, 2);
        when(mockResultSet.getString("text")).thenReturn("Test text 1", "Test text 2");
        when(mockResultSet.getDate("date")).thenReturn(
            java.sql.Date.valueOf("2024-01-01"),
            java.sql.Date.valueOf("2024-01-02")
        );

        // Execute
        List<com.workflow.projet.model.Test> result = testRepository.findAll();

        // Verify
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(1, result.get(0).getId());
        assertEquals("Test text 1", result.get(0).getText());
        assertEquals(2, result.get(1).getId());
        assertEquals("Test text 2", result.get(1).getText());

        verify(mockConnection).prepareStatement("SELECT id, text, date FROM test");
        verify(mockPreparedStatement).executeQuery();
        verify(mockResultSet, atLeastOnce()).next();
        verify(mockResultSet).close();
        verify(mockPreparedStatement).close();
    }

    @Test
    void findAll_EmptyResult() throws Exception {
        org.mockito.MockitoAnnotations.openMocks(this);
        setPrivateStaticField(
            com.workflow.projet.config.DatabaseConnection.class, 
            "connection", 
            mockConnection
        );
        
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(false);

        List<com.workflow.projet.model.Test> result = testRepository.findAll();

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void findAll_ThrowsSQLException() throws Exception {
        org.mockito.MockitoAnnotations.openMocks(this);
        setPrivateStaticField(
            com.workflow.projet.config.DatabaseConnection.class, 
            "connection", 
            mockConnection
        );
        
        when(mockConnection.prepareStatement(anyString())).thenThrow(new SQLException("Connection failed"));

        assertThrows(SQLException.class, () -> testRepository.findAll());
    }

    @Test
    void findById_Found() throws Exception {
        org.mockito.MockitoAnnotations.openMocks(this);
        setPrivateStaticField(
            com.workflow.projet.config.DatabaseConnection.class, 
            "connection", 
            mockConnection
        );
        
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);

        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getInt("id")).thenReturn(1);
        when(mockResultSet.getString("text")).thenReturn("Test text 1");
        when(mockResultSet.getDate("date")).thenReturn(java.sql.Date.valueOf("2024-01-01"));

        com.workflow.projet.model.Test result = testRepository.findById(1);

        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("Test text 1", result.getText());

        verify(mockPreparedStatement).setInt(1, 1);
    }

    @Test
    void findById_NotFound() throws Exception {
        org.mockito.MockitoAnnotations.openMocks(this);
        setPrivateStaticField(
            com.workflow.projet.config.DatabaseConnection.class, 
            "connection", 
            mockConnection
        );
        
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(false);

        com.workflow.projet.model.Test result = testRepository.findById(999);

        assertNull(result);
    }

    @Test
    void findById_ThrowsSQLException() throws Exception {
        org.mockito.MockitoAnnotations.openMocks(this);
        setPrivateStaticField(
            com.workflow.projet.config.DatabaseConnection.class, 
            "connection", 
            mockConnection
        );
        
        when(mockConnection.prepareStatement(anyString())).thenThrow(new SQLException("Connection failed"));

        assertThrows(SQLException.class, () -> testRepository.findById(1));
    }

    private static void setPrivateStaticField(Class<?> clazz, String fieldName, Object value) throws Exception {
        java.lang.reflect.Field field = clazz.getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(null, value);
    }
}

