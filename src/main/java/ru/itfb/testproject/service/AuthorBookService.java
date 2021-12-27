package ru.itfb.testproject.service;

import org.springframework.stereotype.Service;
import ru.itfb.testproject.model.AuthorBook;
import ru.itfb.testproject.repositories.AuthorBookRepository;

import java.util.List;

@Service
public class AuthorBookService {

    private final AuthorBookRepository authorBookRepository;

    public AuthorBookService(AuthorBookRepository authorBookRepository) {
        this.authorBookRepository = authorBookRepository;
    }

    public void create(AuthorBook authorBook) {
        authorBookRepository.save(authorBook);
    }

    public List<AuthorBook> readAll() {
        return authorBookRepository.findAll();
    }

    public boolean update(AuthorBook authorBook, Long id) {
        if (authorBookRepository.existsById(id)) {
            authorBook.setId(id);
            authorBookRepository.save(authorBook);
            return true;
        }

        return false;
    }

    public boolean delete(Long id) {
        if (authorBookRepository.existsById(id)) {
            authorBookRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Long getIdByIDBook(Long id_book) {
        AuthorBook ab = authorBookRepository.findAll().stream()
                .filter(id_b -> id_b.getId_book().equals(id_book))
                .findFirst().orElse(null);
        return ab.getId();
    }
}
