package com.crc.dto;

public class BookDto {
    private Long id;
    private String title;
    private Integer publicationYear;
    private AuthorDto author;

    public BookDto(Long id, String title, Integer publicationYear, AuthorDto author) {
        this.id = id;
        this.title = title;
        this.publicationYear = publicationYear;
        this.author = author;
    }

    public BookDto() {
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

    public AuthorDto getAuthor() {
        return author;
    }

    public void setAuthor(AuthorDto author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "BookDto{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", publicationYear=" + publicationYear +
                ", author=" + author +
                '}';
    }
}
