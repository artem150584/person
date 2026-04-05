-- liquibase formatted sql

-- changeset author-name:kashirskiyaa:20260404_create_table_credential

CREATE TABLE credential
(
    id           UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    series       VARCHAR(255)  NOT NULL,
    token        VARCHAR(255),
    expired_date TIMESTAMP NOT NULL
);

-- Комментарии к колонкам таблицы credentials
COMMENT ON COLUMN credential.series IS 'Серия (ключ) для связи с персоной';
COMMENT ON COLUMN credential.token IS 'Токен';
COMMENT ON COLUMN credential.expired_date IS 'Срок действия токена';

ALTER TABLE person ADD COLUMN series VARCHAR(255);

