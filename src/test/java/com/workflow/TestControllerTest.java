package com.workflow;

import com.workflow.projet.controller.TestController;
import com.workflow.projet.repository.TestRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TestControllerTest {

    @Mock
    private TestRepository testRepository;

    @InjectMocks
    private TestController testController;

    private com.workflow.projet.model.Test test1;
    private com.workflow.projet.model.Test test2;

    @BeforeEach
    void setUp() {
        test1 = new com.workflow.projet.model.Test(1, "Test text 1", "2024-01-01");
        test2 = new com.workflow.projet.model.Test(2, "Test text 2", "2024-01-02");
    }

    @Test
    void testGetAllTests_Success() throws Exception {
        List<com.workflow.projet.model.Test> tests = Arrays.asList(test1, test2);
        when(testRepository.findAll()).thenReturn(tests);

        ResponseEntity<List<com.workflow.projet.model.Test>> response = testController.getAllTests();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        verify(testRepository, times(1)).findAll();
    }

    @Test
    void testGetAllTests_InternalServerError() throws Exception {
        when(testRepository.findAll()).thenThrow(new RuntimeException("Database error"));

        ResponseEntity<List<com.workflow.projet.model.Test>> response = testController.getAllTests();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testGetTestById_Found() throws Exception {
        when(testRepository.findById(1)).thenReturn(test1);

        ResponseEntity<com.workflow.projet.model.Test> response = testController.getTestById(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getId());
        assertEquals("Test text 1", response.getBody().getText());
    }

    @Test
    void testGetTestById_NotFound() throws Exception {
        when(testRepository.findById(999)).thenReturn(null);

        ResponseEntity<com.workflow.projet.model.Test> response = testController.getTestById(999);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testGetTestById_InternalServerError() throws Exception {
        when(testRepository.findById(1)).thenThrow(new RuntimeException("Database error"));

        ResponseEntity<com.workflow.projet.model.Test> response = testController.getTestById(1);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getBody());
    }
}

