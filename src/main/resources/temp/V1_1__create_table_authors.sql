-- Tworzenie tabeli dla Autorów
CREATE TABLE authors (
    id BIGSERIAL PRIMARY KEY,    -- Używamy BIGSERIAL dla autoinkrementującego klucza głównego w PostgreSQL
    name VARCHAR(255) NOT NULL,
    birth_year INTEGER
);