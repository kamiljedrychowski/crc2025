package com.crc.dto;

import java.util.List;

public class AuthorDto {
    private Long id;
    private String name;
    private Integer birthYear;
    private List<BookDto> books;

    public AuthorDto(Long id, String name, Integer birthYear, List<BookDto> books) {
        this.id = id;
        this.name = name;
        this.birthYear = birthYear;
        this.books = books;
    }

    public AuthorDto() {
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

    public List<BookDto> getBooks() {
        return books;
    }

    public void setBooks(List<BookDto> books) {
        this.books = books;
    }

    @Override
    public String toString() {
        return "AuthorDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", birthYear=" + birthYear +
                ", books=" + books +
                '}';
    }
}
