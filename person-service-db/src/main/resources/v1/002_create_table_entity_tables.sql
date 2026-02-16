-- liquibase formatted sql

-- changeset author-name:kashirskiyaa:20260208_create_table_person

ALTER TABLE person RENAME COLUMN username TO name;
ALTER TABLE person
    ADD COLUMN IF NOT EXISTS last_name VARCHAR(50),
    ADD COLUMN IF NOT EXISTS patronymic_name VARCHAR(50),
    ADD COLUMN IF NOT EXISTS identity_document VARCHAR(50),
    ADD COLUMN IF NOT EXISTS contact VARCHAR(50),
    ADD COLUMN IF NOT EXISTS person_address VARCHAR(50);

CREATE TABLE IF NOT EXISTS identity_document
(
    id        UUID PRIMARY KEY,
    type      VARCHAR(50),
    series    VARCHAR(20),
    number    INTEGER,
    person_id UUID NOT NULL,

    CONSTRAINT fk_identity_document_person
        FOREIGN KEY (person_id)
            REFERENCES person (id)
            ON DELETE CASCADE
);


CREATE TABLE IF NOT EXISTS contact
(
    id            UUID PRIMARY KEY,
    contact_value VARCHAR(255),
    person_id     UUID NOT NULL,

    CONSTRAINT fk_contact_person
        FOREIGN KEY (person_id)
            REFERENCES person (id)
            ON DELETE CASCADE
);


CREATE TABLE IF NOT EXISTS address
(
    id       UUID PRIMARY KEY,
    zip_code VARCHAR(20),
    city     VARCHAR(100),
    street   VARCHAR(255),
    home     INTEGER,
    flat     INTEGER
);

CREATE TABLE IF NOT EXISTS person_address
(
    person_id  UUID REFERENCES person (id) ON DELETE CASCADE,
    address_id UUID REFERENCES address (id) ON DELETE CASCADE,
    PRIMARY KEY (person_id, address_id)
);