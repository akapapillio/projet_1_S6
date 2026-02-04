package com.workflow.projet.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.workflow.projet.config.DatabaseConnection;
import com.workflow.projet.model.Test;

public class TestRepository {

    public List<Test> findAll() throws SQLException {
        List<Test> tests = new ArrayList<>();
        String query = "SELECT id, text, date FROM test";
        
        Connection conn = DatabaseConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement(query);
        ResultSet rs = stmt.executeQuery();
        
        while (rs.next()) {
            Test test = new Test();
            test.setId(rs.getInt("id"));
            test.setText(rs.getString("text"));
            test.setDate(rs.getDate("date"));
            tests.add(test);
        }
        
        rs.close();
        stmt.close();
        
        return tests;
    }

    public Test findById(int id) throws SQLException {
        String query = "SELECT id, text, date FROM test WHERE id = ?";
        
        Connection conn = DatabaseConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();
        
        Test test = null;
        if (rs.next()) {
            test = new Test();
            test.setId(rs.getInt("id"));
            test.setText(rs.getString("text"));
            test.setDate(rs.getDate("date"));
        }
        
        rs.close();
        stmt.close();
        
        return test;
    }
}

