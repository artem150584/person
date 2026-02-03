-- liquibase formatted sql

-- changeset author-name:kashirskiyaa:20260203_create_table_person
CREATE EXTENSION IF NOT EXISTS "pgcrypto";

CREATE TABLE person (
                       id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                       username VARCHAR(50) NOT NULL UNIQUE,
                       email VARCHAR(100) NOT NULL UNIQUE
);