package com.crc.controller;

import com.crc.entity.Author;
import com.crc.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/author")
public class AuthorController {

    private final AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    /**
     * Dodaje nowego autora.
     * Oczekuje obiektu Author w ciele żądania POST.
     * Zwraca utworzonego autora i status 201 Created.
     * Przykład użycia (np. curl):
     * curl -X POST -H "Content-Type: application/json" -d '{"name": "Adam Mickiewicz", "birthYear": 1798}' http://localhost:8080/api/library/authors
     */
    @PostMapping("/authors")
    public ResponseEntity<Author> addAuthor(@RequestBody Author author) {
        // Uwaga: W rzeczywistej aplikacji lepiej użyć DTO (Data Transfer Object) zamiast bezpośrednio encji w @RequestBody
        // Tutaj dla uproszczenia używamy encji.
        // Zakładamy, że ID w przychodzącym autorze jest null lub ignorowane.
        Author savedAuthor = authorService.addAuthor(author.getName(), author.getBirthYear());
        return ResponseEntity.status(HttpStatus.CREATED).body(savedAuthor);
    }

    /**
     * Pobiera listę wszystkich autorów.
     * Zwraca listę autorów i status 200 OK.
     * Przykład użycia: curl http://localhost:8080/api/library/authors
     */
    @GetMapping("/authors")
    public ResponseEntity<List<Author>> getAllAuthors() {
        List<Author> authors = authorService.findAllAuthors();
        return ResponseEntity.ok(authors); // Zwraca 200 OK z listą autorów
    }

    /**
     * Pobiera konkretnego autora po jego ID.
     * Zwraca autora i status 200 OK, jeśli znaleziono.
     * Zwraca status 404 Not Found, jeśli autor nie istnieje.
     * Przykład użycia: curl http://localhost:8080/api/library/authors/1
     */
    @GetMapping("/authors/{id}")
    public ResponseEntity<Author> getAuthorById(@PathVariable Long id) {
        Optional<Author> authorOptional = authorService.findAuthorById(id);
        return authorOptional
                .map(ResponseEntity::ok) // Jeśli autor istnieje, zwróć 200 OK z autorem
                .orElseGet(() -> ResponseEntity.notFound().build()); // Jeśli nie istnieje, zwróć 404 Not Found
    }

    /**
     * Wyszukuje autora po imieniu (parametr zapytania 'name').
     * Zwraca autora i status 200 OK, jeśli znaleziono.
     * Zwraca status 404 Not Found, jeśli autor o podanym imieniu nie istnieje.
     * Przykład użycia: curl "http://localhost:8080/api/library/authors/search?name=Jan%20Kowalski"
     */
    @GetMapping("/authors/search")
    public ResponseEntity<Author> findAuthorByName(@RequestParam String name) {
        Optional<Author> authorOptional = authorService.findAuthorByName(name);
        return authorOptional
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    /**
     * Usuwa autora o podanym ID.
     * Zwraca status 204 No Content, jeśli usunięcie się powiodło.
     * (Opcjonalnie można dodać sprawdzanie czy autor istnieje i zwracać 404)
     * Przykład użycia: curl -X DELETE http://localhost:8080/api/library/authors/1
     */
    @DeleteMapping("/authors/{id}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable Long id) {
        // Dobrą praktyką byłoby sprawdzenie, czy autor istnieje przed próbą usunięcia
        Optional<Author> authorOptional = authorService.findAuthorById(id);
        if (authorOptional.isEmpty()) {
            return ResponseEntity.notFound().build(); // Zwróć 404 jeśli autor nie istnieje
        }
        authorService.deleteAuthor(id);
        return ResponseEntity.noContent().build(); // Zwraca 204 No Content
    }
}
