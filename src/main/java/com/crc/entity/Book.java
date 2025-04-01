package com.crc.entity;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "publication_year")
    private Integer publicationYear;

    // Relacja wiele-do-jednego (wiele książek -> jeden autor)
    // fetch = FetchType.LAZY - autor będzie ładowany tylko na żądanie
    // @JoinColumn(name = "author_id") - określa nazwę kolumny klucza obcego w tabeli 'books'
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id") // Nazwa kolumny w tabeli `books` przechowującej ID autora
    private Author author;

    // Konstruktor ułatwiający tworzenie obiektu
    public Book(String title, Integer publicationYear) {
        this.title = title;
        this.publicationYear = publicationYear;
    }

    // Przesłonięcie equals() i hashCode()
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return id != null && Objects.equals(id, book.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    // Ręczne zaimplementowanie toString() aby uniknąć StackOverflowError
    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", publicationYear=" + publicationYear +
                // Możemy dodać ID autora, ale nie cały obiekt Author
                ", authorId=" + (author != null ? author.getId() : "null") +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(Integer publicationYear) {
        this.publicationYear = publicationYear;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Book() {
    }
}