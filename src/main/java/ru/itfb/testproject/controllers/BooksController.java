package ru.itfb.testproject.controllers;

import org.springframework.web.bind.annotation.*;
import ru.itfb.testproject.exceptions.BookNotFound;
import ru.itfb.testproject.model.*;
import ru.itfb.testproject.service.AuthorBookService;
import ru.itfb.testproject.service.AuthorService;
import ru.itfb.testproject.service.BookService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("books")
public class BooksController {

    private final BookService bookService;
    private final AuthorService authorService;
    private final AuthorBookService authorBookService;


    public BooksController(BookService bookService, AuthorService authorService, AuthorBookService authorBookService) {
        this.bookService = bookService;
        this.authorService = authorService;
        this.authorBookService = authorBookService;
    }

    @GetMapping
    public List<Map<String, String>> books() {
        List<Map<String, String>> books = new ArrayList<>();
        bookService.readAll().forEach(book -> books.add(book.toMap()));
        return books;
    }

    @GetMapping("{id}")
    public Map<String, String> getOne(@PathVariable String id) {
        return getBook(id).toMap();
    }

    private Book getBook(String id) {
        return bookService.readAll().stream()
                .filter(book -> book.getId().toString().equals(id))
                .findFirst()
                .orElseThrow(BookNotFound::new);
    }

    private boolean hasBook(Book book) {
        Book book1 = bookService.readAll().stream()
                .filter(b -> b.getId().equals(book.getId()))
                .findFirst().orElse(null);
        return book1 != null;
    }

    private boolean hasAuthor(Author author) {
        Author author1 = authorService.readAll().stream()
                .filter(a -> a.equals(author))
                .findFirst().orElse(null);
        return author1 != null;
    }

    private Book getLastBook() {
        return bookService.readAll().get(bookService.readAll().size() - 1);
    }

    private Long getAuthorId(Author author) {
        Author it = authorService.readAll().stream()
                .filter(a -> a.equals(author))
                .findFirst().orElse(null);
        return it != null ? it.getId() : authorService.readAll().size();
    }

    @PostMapping
    public Book create(@RequestBody AuthorBookDTO authorBookDTO) {
        if (!hasBook(authorBookDTO.getBook())) {
            bookService.create(authorBookDTO.getBook());
            if (!hasAuthor(authorBookDTO.getAuthor()))
                authorService.create(authorBookDTO.getAuthor());
            AuthorBook ab = new AuthorBook(5L, getAuthorId(authorBookDTO.getAuthor()), getLastBook().getId());
            authorBookService.create(ab);
        }
        return authorBookDTO.getBook();
    }

    @PutMapping("{id}") // TODO что тут апдейтить
    public Map<String, String> update(@PathVariable String id, @RequestBody Book book) {
        bookService.update(book, Long.parseLong(id, 10));
        return book.toMap();
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable String id) {
        authorBookService.delete(authorBookService.getIdByIDBook(Long.parseLong(id, 10)));
        bookService.delete(Long.parseLong(id, 10));
    }
}