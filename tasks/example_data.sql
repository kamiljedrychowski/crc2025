create schema crc_sql_demo;
create TABLE authors (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    birth_year INTEGER
);

-- Tworzenie tabeli dla Książek
create TABLE books (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    publication_year INTEGER,
    author_id BIGINT,            -- Klucz obcy wskazujący na autora

    -- Definicja klucza obcego z ograniczeniem
    CONSTRAINT fk_author
        FOREIGN KEY(author_id)
        REFERENCES authors(id)
        ON delete SET NULL
);

-- Opcjonalnie: Dodanie indeksu dla klucza obcego dla lepszej wydajności zapytań
create index idx_book_author_id on books(author_id);

insert into AUTHORS (NAME, BIRTH_YEAR) values ('AUTOR1', 1990);
insert into AUTHORS (NAME, BIRTH_YEAR) values ('AUTOR2', 1970);
insert into AUTHORS (NAME, BIRTH_YEAR) values ('AUTOR3', 1950);
insert into AUTHORS (NAME, BIRTH_YEAR) values ('AUTOR4', 2000);

insert into BOOKS (title, publication_year, author_id) values ('TITLE1', 1999, 3);
insert into BOOKS (title, publication_year, author_id) values ('TITLE2', 2005, 2);
insert into BOOKS (title, publication_year, author_id) values ('TITLE3', 2020, 4);
insert into BOOKS (title, publication_year, author_id) values ('TITLE4', 2021, 4);



--DROP SCHEMA crc_sql_demo CASCADE;
--SELECT *  FROM pg_catalog.pg_namespace;
--create schema crc_sql_demo;