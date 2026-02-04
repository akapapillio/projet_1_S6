package com.workflow.projet.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.workflow.projet.model.Test;
import com.workflow.projet.repository.TestRepository;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class TestController {

    private TestRepository testRepository = new TestRepository();

    @GetMapping("/tests")
    public ResponseEntity<List<Test>> getAllTests() {
        try {
            List<Test> tests = testRepository.findAll();
            return ResponseEntity.ok(tests);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }

    @GetMapping("/tests/{id}")
    public ResponseEntity<Test> getTestById(@PathVariable int id) {
        try {
            Test test = testRepository.findById(id);
            if (test != null) {
                return ResponseEntity.ok(test);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }
}

