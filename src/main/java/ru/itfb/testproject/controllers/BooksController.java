package ru.itfb.testproject.controllers;

import org.springframework.web.bind.annotation.*;
import ru.itfb.testproject.exceptions.BookNotFound;
import ru.itfb.testproject.model.Book;
import ru.itfb.testproject.service.BookService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("books")
public class BooksController {

    private final BookService bookService;


    public BooksController(BookService bookService) {
        this.bookService = bookService;
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

    @PostMapping
    public Book create(@RequestBody Book book) {
        bookService.create(book);
        return book;
    }

    @PutMapping("{id}")
    public Map<String, String> update(@PathVariable String id, @RequestBody Book book) {
        bookService.update(book, Long.parseLong(id, 10));
        return book.toMap();
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable String id) {
        bookService.delete(Long.parseLong(id, 10));
    }
}