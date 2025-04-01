package com.crc.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "authors")
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Strategia generowania ID dla PostgreSQL (IDENTITY lub SEQUENCE)
    private Long id;

    @Column(name = "name", nullable = false, length = 255) // Mapowanie kolumny 'name'
    private String name;

    @Column(name = "birth_year")
    private Integer birthYear;

    // Relacja jeden-do-wielu (jeden autor -> wiele książek)
    // mappedBy = "author" wskazuje na pole 'author' w encji Book, które zarządza tą relacją
    // cascade = CascadeType.ALL - operacje (persist, merge, remove, refresh, detach) na autorze będą kaskadowane na książki
    // orphanRemoval = true - jeśli książka zostanie usunięta z kolekcji 'books', zostanie również usunięta z bazy danych
    // fetch = FetchType.LAZY - książki będą ładowane dopiero, gdy będą potrzebne (zalecane dla wydajności)
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Book> books = new ArrayList<>();

    public Author(String name, Integer birthYear) {
        this.name = name;
        this.birthYear = birthYear;
    }

    public void addBook(Book book) {
        books.add(book);
        book.setAuthor(this);
    }

    public void removeBook(Book book) {
        books.remove(book);
        book.setAuthor(null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Author author = (Author) o;
        return id != null && Objects.equals(id, author.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    // Ręczne zaimplementowanie toString() aby uniknąć StackOverflowError
    @Override
    public String toString() {
        return "Author{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", birthYear=" + birthYear +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(Integer birthYear) {
        this.birthYear = birthYear;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public Author() {
    }
}