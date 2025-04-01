package com.crc.service;

import com.crc.dto.BookDto;
import com.crc.entity.Author;
import com.crc.entity.Book;
import com.crc.helpers.BookMapper;
import com.crc.repository.AuthorRepository;
import com.crc.repository.BookRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

import static com.crc.helpers.BookMapper.mapBookToDto;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    @Autowired
    public BookService(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }

    @Transactional
    public Optional<BookDto> addBookToAuthor(Long authorId, String title, Integer publicationYear) {
        Optional<Author> authorOptional = authorRepository.findById(authorId);
        if (authorOptional.isPresent()) {
            Author author = authorOptional.get();
            Book book = new Book(title, publicationYear);
            book.setAuthor(author);
            return Optional.of(mapBookToDto(bookRepository.save(book)));
        } else {
            System.err.println("Nie znaleziono autora o ID: " + authorId);
            return Optional.empty();
        }
    }

    @Transactional
    public Optional<BookDto> addBook(String title, Integer publicationYear, Author author) {
        if (author == null || author.getId() == null) {
            System.err.println("Autor jest null lub nie został zapisany w bazie.");
            return Optional.empty();
        }
        Optional<Author> managedAuthorOpt = authorRepository.findById(author.getId());
        if (managedAuthorOpt.isEmpty()) {
            System.err.println("Autor o ID: " + author.getId() + " nie istnieje w bazie.");
            return Optional.empty();
        }

        Book book = new Book(title, publicationYear);
        book.setAuthor(managedAuthorOpt.get());
        return Optional.of(mapBookToDto(bookRepository.save(book)));
    }


    public Optional<Book> findBookById(Long id) {
        return bookRepository.findById(id);
    }

    public List<BookDto> findAllBooks() {
        return bookRepository.findAll().stream().map(BookMapper::mapBookToDto).toList();
    }

    public List<Book> findBooksByAuthorId(Long authorId) {
        return bookRepository.findByAuthorId(authorId);
    }

    public List<Book> findBooksByTitle(String title) {
        return bookRepository.findByTitleContainingIgnoreCase(title);
    }

    @Transactional
    public void deleteBook(Long bookId) {
        bookRepository.deleteById(bookId);
    }

}
