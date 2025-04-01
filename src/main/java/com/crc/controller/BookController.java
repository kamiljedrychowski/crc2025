package com.crc.controller;

import com.crc.dto.BookDto;
import com.crc.entity.Author;
import com.crc.entity.Book;
import com.crc.service.AuthorService;
import com.crc.service.BookJdbcService;
import com.crc.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/book")
public class BookController {

    private final BookService bookService;
    private final AuthorService authorService;
    private final BookJdbcService bookJdbcService;

    @Autowired
    public BookController(BookService bookService, AuthorService authorService, BookJdbcService bookJdbcService) {
        this.bookService = bookService;
        this.authorService = authorService;
        this.bookJdbcService = bookJdbcService;
    }

    /**
     * Dodaje nową książkę dla konkretnego autora.
     * Oczekuje ID autora w ścieżce i danych książki w ciele żądania POST.
     * Zwraca utworzoną książkę i status 201 Created.
     * Zwraca 404 Not Found, jeśli autor o podanym ID nie istnieje.
     * Przykład użycia:
     * curl -X POST -H "Content-Type: application/json" -d '{"title": "Pan Tadeusz", "publicationYear": 1834}' http://localhost:8080/api/library/authors/1/books
     */
    @PostMapping("/authors/{authorId}/books")
    public ResponseEntity<BookDto> addBookToAuthor(@PathVariable Long authorId, @RequestBody Book book) {
        // Ponownie, DTO byłoby lepsze niż encja Book
        Optional<BookDto> savedBookOptional = bookService.addBookToAuthor(authorId, book.getTitle(), book.getPublicationYear());
        return savedBookOptional
                .map(savedBook -> ResponseEntity.status(HttpStatus.CREATED).body(savedBook)) // Jeśli się udało, 201 Created
                .orElseGet(() -> ResponseEntity.notFound().build()); // Jeśli autor nie istnieje, 404 Not Found
    }


    /**
     * Pobiera listę wszystkich książek.
     * Zwraca listę książek i status 200 OK.
     * Przykład użycia: curl http://localhost:8080/api/library/books
     */
    @GetMapping("/books")
    public ResponseEntity<List<BookDto>> getAllBooks() {
        List<BookDto> books = bookService.findAllBooks();
        return ResponseEntity.ok(books);
    }

    /**
     * Pobiera konkretną książkę po jej ID.
     * Zwraca książkę i status 200 OK, jeśli znaleziono.
     * Zwraca status 404 Not Found, jeśli książka nie istnieje.
     * Przykład użycia: curl http://localhost:8080/api/library/books/1
     */
    @GetMapping("/books/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        Optional<Book> bookOptional = bookService.findBookById(id);
        return bookOptional
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Pobiera wszystkie książki dla konkretnego autora.
     * Zwraca listę książek i status 200 OK. Może zwrócić pustą listę.
     * Zwraca 404 Not Found, jeśli autor o podanym ID nie istnieje.
     * Przykład użycia: curl http://localhost:8080/api/library/authors/1/books
     */
    @GetMapping("/authors/{authorId}/books")
    public ResponseEntity<List<Book>> getBooksByAuthor(@PathVariable Long authorId) {
        // Sprawdźmy najpierw, czy autor istnieje
        Optional<Author> authorOptional = authorService.findAuthorById(authorId);
        if (authorOptional.isEmpty()) {
            return ResponseEntity.notFound().build(); // Autor nie znaleziony, więc nie może mieć książek
        }
        List<Book> books = bookService.findBooksByAuthorId(authorId);
        return ResponseEntity.ok(books);
    }

    /**
     * Wyszukuje książki po fragmencie tytułu (parametr zapytania 'title').
     * Zwraca listę pasujących książek i status 200 OK. Może zwrócić pustą listę.
     * Przykład użycia: curl "http://localhost:8080/api/library/books/search?title=Książka"
     */
    @GetMapping("/books/search")
    public ResponseEntity<List<Book>> findBooksByTitle(@RequestParam String title) {
        List<Book> books = bookService.findBooksByTitle(title);
        return ResponseEntity.ok(books);
    }


    /**
     * Usuwa książkę o podanym ID.
     * Zwraca status 204 No Content, jeśli usunięcie się powiodło.
     * (Opcjonalnie można dodać sprawdzanie czy książka istnieje i zwracać 404)
     * Przykład użycia: curl -X DELETE http://localhost:8080/api/library/books/1
     */
    @DeleteMapping("/books/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        // Dobrą praktyką byłoby sprawdzenie, czy książka istnieje przed próbą usunięcia
        Optional<Book> bookOptional = bookService.findBookById(id);
        if (bookOptional.isEmpty()) {
            return ResponseEntity.notFound().build(); // Zwróć 404 jeśli książka nie istnieje
        }
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build(); // Zwraca 204 No Content
    }

    @GetMapping("/books/number")
    public Integer getNumberOfBooks() {
        return this.bookJdbcService.getBooksCount();
    }

}
