package ru.itfb.testproject.service;

import org.springframework.stereotype.Service;
import ru.itfb.testproject.exceptions.BookNotFound;
import ru.itfb.testproject.model.Book;
import ru.itfb.testproject.repositories.BookRepository;

import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public void create(Book book) {
        bookRepository.save(book);
    }

    public List<Book> readAll(){
        return bookRepository.findAll();
    }

    public boolean update(Book book, Long id) {
        if (bookRepository.existsById(id)) {
            book.setId(id);
            bookRepository.save(book);
            return true;
        }

        return false;
    }

    public boolean delete(Long id) {
        if (bookRepository.existsById(id)) {
            bookRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public boolean hasBook(Book book) {
        Book book1 = readAll().stream()
                .filter(b -> b.getName().equals(book.getName()))
                .findFirst().orElse(null);
        return book1 != null;
    }

    public Book getLastBook() {
        return readAll().get(readAll().size() - 1);
    }

    public Book getBook(String id) {
        return readAll().stream()
                .filter(book -> book.getId().toString().equals(id))
                .findFirst()
                .orElseThrow(BookNotFound::new);
    }

}
