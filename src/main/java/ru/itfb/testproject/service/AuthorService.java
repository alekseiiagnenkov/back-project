package ru.itfb.testproject.service;

import org.springframework.stereotype.Service;
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
}
