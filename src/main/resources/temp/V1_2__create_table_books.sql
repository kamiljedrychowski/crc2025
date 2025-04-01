-- Tworzenie tabeli dla Książek
CREATE TABLE books (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    publication_year INTEGER,
    author_id BIGINT,            -- Klucz obcy wskazujący na autora

    -- Definicja klucza obcego z ograniczeniem
    CONSTRAINT fk_author
        FOREIGN KEY(author_id)
        REFERENCES authors(id)
        ON DELETE SET NULL -- Co zrobić, gdy autor zostanie usunięty? (Można też użyć CASCADE, RESTRICT, NO ACTION)
);

-- Opcjonalnie: Dodanie indeksu dla klucza obcego dla lepszej wydajności zapytań
CREATE INDEX idx_book_author_id ON books(author_id);