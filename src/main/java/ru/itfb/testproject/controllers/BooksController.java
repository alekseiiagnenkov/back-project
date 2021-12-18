package ru.itfb.testproject.controllers;

import org.springframework.web.bind.annotation.*;
import ru.itfb.testproject.exceptions.NotFoundException;
import ru.itfb.testproject.model.Book;
import ru.itfb.testproject.service.BookService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("booksJSON")
public class BooksController {

    private int counter = 1;

    private BookService bookService;


    public BooksController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public List<Map<String, String>> books() {
        List<Map<String,String>> books = new ArrayList<>();
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
                .orElseThrow(NotFoundException::new);
    }

/*    @PostMapping
    public Map<String, String> create(@RequestBody Map<String, String> book) {
        book.put("id", String.valueOf(counter++));
        books.add(book);
        return book;
    }

    @PutMapping("{id}")
    public Map<String, String> update(@PathVariable String id, @RequestBody Map<String, String> book) {
        Map<String, String> bookFromDB = getBook(id);

        bookFromDB.putAll(book);
        bookFromDB.put("id", id);
        return bookFromDB;
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable String id) {
        Map<String, String> book = getBook("id");
        books.remove(book);
    }*/
}