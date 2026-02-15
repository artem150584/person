-- liquibase formatted sql

-- changeset author-name:kashirskiyaa:20260208_create_table_person

ALTER TABLE person
    DROP COLUMN email;
ALTER TABLE person
    RENAME COLUMN username TO name;
ALTER TABLE person
    ADD COLUMN IF NOT EXISTS last_name VARCHAR(50);
ALTER TABLE person
    ADD COLUMN IF NOT EXISTS patronymic_name VARCHAR(50);
ALTER TABLE person
    ADD COLUMN IF NOT EXISTS identity_document VARCHAR(50);
ALTER TABLE person
    ADD COLUMN IF NOT EXISTS contact VARCHAR(50);
ALTER TABLE person
    ADD COLUMN IF NOT EXISTS person_address VARCHAR(50);

CREATE TABLE identity_document
(
    id        SERIAL PRIMARY KEY,
    type      VARCHAR(50),
    series    VARCHAR(20),
    number    INTEGER,
    person_id UUID NOT NULL,

    CONSTRAINT fk_identity_document_person
        FOREIGN KEY (person_id)
            REFERENCES person (id)
            ON DELETE CASCADE
);


CREATE TABLE contact
(
    id            SERIAL PRIMARY KEY,
    contact_value VARCHAR(255),
    person_id     UUID NOT NULL,

    CONSTRAINT fk_contact_person
        FOREIGN KEY (person_id)
            REFERENCES person (id)
            ON DELETE CASCADE
);


CREATE TABLE address
(
    id       SERIAL PRIMARY KEY,
    zip_code VARCHAR(20),
    city     VARCHAR(100),
    street   VARCHAR(255),
    home     INTEGER,
    flat     INTEGER
);

CREATE TABLE person_address
(
    person_id  UUID REFERENCES person (id) ON DELETE CASCADE,
    address_id INTEGER REFERENCES address (id) ON DELETE CASCADE,
    PRIMARY KEY (person_id, address_id)
);