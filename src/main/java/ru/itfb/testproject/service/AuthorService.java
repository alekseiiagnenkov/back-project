package ru.itfb.testproject.service;

import org.springframework.stereotype.Service;
import ru.itfb.testproject.exceptions.BookNotFound;
import ru.itfb.testproject.model.Author;
import ru.itfb.testproject.repositories.AuthorRepository;

import java.util.List;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public void create(Author author) {
        authorRepository.save(author);
    }

    public List<Author> readAll() {
        return authorRepository.findAll();
    }

    public boolean update(Author author, Long id) {
        if (authorRepository.existsById(id)) {
            author.setId(id);
            authorRepository.save(author);
            return true;
        }

        return false;
    }

    public boolean delete(Long id) {
        if (authorRepository.existsById(id)) {
            authorRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public boolean hasAuthor(Author author) {
        Author author1 = readAll().stream()
                .filter(a -> a.equals(author))
                .findFirst().orElse(null);
        return author1 != null;
    }

    public Long getAuthorId(Author author) {
        Author it = readAll().stream()
                .filter(a -> a.equals(author))
                .findFirst().orElse(null);
        return it != null ? it.getId() : readAll().size();
    }

    public Author getAuthor(String id) {
        return readAll().stream()
                .filter(author -> author.getId().toString().equals(id))
                .findFirst()
                .orElse(null);
    }

}
