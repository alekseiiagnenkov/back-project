package ru.itfb.testproject.service;

import org.springframework.stereotype.Service;
import ru.itfb.testproject.model.AuthorBook;
import ru.itfb.testproject.repositories.AuthorBookRepository;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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

    public List<AuthorBook> getIdsByIdAuthor(Long id_author){
        return authorBookRepository.findAll().stream()
                .filter(ab -> Objects.equals(ab.getId_author(), id_author))
                .collect(Collectors.toList());
    }

    public AuthorBook getIdByIDBook(Long id_book) {
        return authorBookRepository.findAll().stream()
                .filter(id_b -> id_b.getId_book().equals(id_book))
                .findFirst().orElse(null);
    }
}
