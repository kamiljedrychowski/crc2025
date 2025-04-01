package com.crc.service;

import com.crc.entity.Author;
import com.crc.repository.AuthorRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public Author addAuthor(String name, Integer birthYear) {
        Author author = new Author(name, birthYear);
        return authorRepository.save(author);
    }

    public Optional<Author> findAuthorById(Long id) {
        return authorRepository.findById(id);
    }

    public Optional<Author> findAuthorByName(String name) {
        return authorRepository.findByName(name);
    }

    public List<Author> findAllAuthors() {
        return authorRepository.findAll();
    }

    @Transactional
    public void deleteAuthor(Long authorId) {
        authorRepository.deleteById(authorId);
    }
}
