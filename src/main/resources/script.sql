-- Script SQL pour la base de données projet_1_s6
-- Créer la base de données et la table

CREATE DATABASE projet_1_s6;
\c projet_1_s6;

CREATE TABLE test(
    id INT PRIMARY KEY,
    text TEXT NOT NULL,
    date DATE NOT NULL
);

INSERT INTO test VALUES (1, 'Hello world', '2026-01-30');

