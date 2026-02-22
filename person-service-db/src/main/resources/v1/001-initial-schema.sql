-- liquibase formatted sql

-- changeset author-name:kashirskiyaa:20260203_create_table_person
CREATE EXTENSION IF NOT EXISTS "pgcrypto";

CREATE TABLE person
(
    id                UUID PRIMARY KEY DEFAULT gen_random_uuid(), -- Уникальный идентификатор
    first_name        VARCHAR(50) NOT NULL,                        -- Имя
    middle_name       VARCHAR(50) NOT NULL,                        -- Отчество
    last_name         VARCHAR(50) NOT NULL,                        -- Фамилия
    identity_document VARCHAR(50),                                 -- Информационное поле о документе
    contact           VARCHAR(50),                                 -- Информационное поле о контактах
    person_address    VARCHAR(50),                                 -- Информационное поле об адресе
    visible           BOOLEAN          DEFAULT true                -- Флаг для мягкого удаления (видимость)
);


CREATE TABLE identity_document
(
    id        UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    type      VARCHAR(50) NOT NULL, -- Тип (Enum в Java)
    series    VARCHAR(20) NOT NULL, -- Серия и номер
    person_id UUID        NOT NULL, -- Ссылка на владельца

    CONSTRAINT fk_identity_document_person
        FOREIGN KEY (person_id)
            REFERENCES person (id)
            ON DELETE CASCADE
);


CREATE TABLE contact
(
    id           UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    phone_number VARCHAR(20), -- Номер телефона
    email        VARCHAR(50), -- Электронная почта
    person_id    UUID        NOT NULL,

    CONSTRAINT fk_contact_person
        FOREIGN KEY (person_id)
            REFERENCES person (id)
            ON DELETE CASCADE
);


CREATE TABLE address
(
    id       UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    zip_code VARCHAR(20),      -- Почтовый индекс
    city     VARCHAR(100) NOT NULL, -- Город
    street   VARCHAR(255) NOT NULL, -- Улица
    home     INTEGER      NOT NULL, -- Дом
    flat     INTEGER                -- Квартира
);

CREATE TABLE person_address
(
    person_id  UUID REFERENCES person (id) ON DELETE CASCADE,
    address_id UUID REFERENCES address (id) ON DELETE CASCADE,
    PRIMARY KEY (person_id, address_id)
);