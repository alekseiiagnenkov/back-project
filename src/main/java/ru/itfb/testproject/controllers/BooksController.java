package ru.itfb.testproject.controllers;

import org.springframework.web.bind.annotation.*;
import ru.itfb.testproject.exceptions.NotFoundException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("booksJSON")
public class BooksController {

    private int counter = 3;

    private List<Map<String, String>> books = new ArrayList<>() {{
        add(new HashMap<>() {{
            put("id", "1");
            put("name", "12 chairs");
            put("author", "Evgeny Petrov");
        }});
        add(new HashMap<>() {{
            put("id", "2");
            put("name", "War and Peace");
            put("author", "Lev Tolstoy");
        }});
    }};

    @GetMapping
    public List<Map<String, String>> books() {
        return books;
    }

    @GetMapping("{id}")
    public Map<String, String> getOne(@PathVariable String id) {
        return getBook(id);
    }

    private Map<String, String> getBook(String id) {
        return books.stream()
                .filter(book -> book.get("id").equals(id))
                .findFirst()
                .orElseThrow(NotFoundException::new);
    }

    @PostMapping
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
    }
}