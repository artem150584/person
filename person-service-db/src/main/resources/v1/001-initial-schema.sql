-- liquibase formatted sql

-- changeset author-name:kashirskiyaa:20260203_create_table_person
CREATE EXTENSION IF NOT EXISTS "pgcrypto";

CREATE TABLE person
(
    id                UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    first_name        VARCHAR(50) NOT NULL,
    middle_name       VARCHAR(50) NOT NULL,
    last_name         VARCHAR(50) NOT NULL,
    identity_document VARCHAR(50),
    contact           VARCHAR(50),
    person_address    VARCHAR(50),
    visible           BOOLEAN DEFAULT true
);


CREATE TABLE identity_document
(
    id        UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    type      VARCHAR(50) NOT NULL,
    series    VARCHAR(20) NOT NULL,
    person_id UUID NOT NULL,

    CONSTRAINT fk_identity_document_person
        FOREIGN KEY (person_id)
            REFERENCES person (id)
            ON DELETE CASCADE
);


CREATE TABLE contact
(
    id           UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    phone_number VARCHAR(20),
    email        VARCHAR(50),
    person_id    UUID NOT NULL,

    CONSTRAINT fk_contact_person
        FOREIGN KEY (person_id)
            REFERENCES person (id)
            ON DELETE CASCADE
);


CREATE TABLE address
(
    id       UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    zip_code VARCHAR(20),
    city     VARCHAR(100) NOT NULL,
    street   VARCHAR(255) NOT NULL,
    home     INTEGER NOT NULL,
    flat     INTEGER
);

CREATE TABLE person_address
(
    person_id  UUID REFERENCES person (id) ON DELETE CASCADE,
    address_id UUID REFERENCES address (id) ON DELETE CASCADE,
    PRIMARY KEY (person_id, address_id)
);