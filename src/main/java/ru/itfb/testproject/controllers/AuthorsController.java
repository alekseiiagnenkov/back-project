package ru.itfb.testproject.controllers;

import org.springframework.web.bind.annotation.*;
import ru.itfb.testproject.model.Author;
import ru.itfb.testproject.model.AuthorBook;
import ru.itfb.testproject.service.AuthorBookService;
import ru.itfb.testproject.service.AuthorService;
import ru.itfb.testproject.service.BookService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("authors")
public class AuthorsController {

    private final BookService bookService;
    private final AuthorService authorService;
    private final AuthorBookService authorBookService;

    public AuthorsController(BookService bookService, AuthorService authorService, AuthorBookService authorBookService) {
        this.bookService = bookService;
        this.authorService = authorService;
        this.authorBookService = authorBookService;
    }

    @GetMapping
    public List<Author> getAuthors() {
        return new ArrayList<>(authorService.readAll());
    }

    @GetMapping("{id}")
    public Map<String, String> getOne(@PathVariable String id) {
        return authorService.getAuthor(id).toMap();
    }

    @PostMapping
    public Map<String, String> create(@RequestBody Author author) {
        author.setId(-1L);
        authorService.create(author);
        return author.toMap();
    }

    @PutMapping("{id}")
    public Map<String, String> update(@PathVariable String id, @RequestBody Author author) {
        if (authorService.hasAuthor(author))
            if (authorService.getAuthorId(author) == Long.parseLong(id, 10))
                authorService.update(author, Long.parseLong(id, 10));
        return author.toMap();
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable String id) {
        List<AuthorBook> ids_books = authorBookService.getIdsByIdAuthor(Long.parseLong(id, 10));

        for (AuthorBook it : ids_books) {
            bookService.delete(it.getId_book());
            authorBookService.delete(it.getId());
        }

        authorService.delete(Long.parseLong(id, 10));
    }
}

