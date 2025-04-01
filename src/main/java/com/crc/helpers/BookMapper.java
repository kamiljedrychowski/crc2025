package com.crc.helpers;

import com.crc.dto.AuthorDto;
import com.crc.dto.BookDto;
import com.crc.entity.Author;
import com.crc.entity.Book;

import java.util.Collections;

public class BookMapper {

    public static BookDto mapBookToDto(Book book) {
        return new BookDto(book.getId(), book.getTitle(), book.getPublicationYear(), mapAuthorToDto(book.getAuthor()));
    }

    public static AuthorDto mapAuthorToDto(Author author) {
        return new AuthorDto(author.getId(), author.getName(), author.getBirthYear(), Collections.emptyList());
    }

    public static AuthorDto mapAuthorToDtoWithBooks(Author author) {
        return new AuthorDto(author.getId(), author.getName(), author.getBirthYear(), author.getBooks().stream().map(BookMapper::mapBookToDto).toList());
    }

}
