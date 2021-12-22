package ru.itfb.testproject.controllers;

import org.springframework.web.bind.annotation.*;
import ru.itfb.testproject.exceptions.BookNotFound;
import ru.itfb.testproject.model.Author;
import ru.itfb.testproject.service.AuthorService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("authors")
public class AuthorsController {

    private AuthorService authorService;

    public AuthorsController(AuthorService authorService) {
        this.authorService = authorService;
    }

    private Author getAuthor(String id) {
        return authorService.readAll().stream()
                .filter(author -> author.getId().toString().equals(id))
                .findFirst()
                .orElseThrow(BookNotFound::new);
    }

    @GetMapping
    public List<Map<String, String>> getAuthors() {
        List<Map<String, String>> res = new ArrayList<>();
        authorService.readAll().forEach(author -> res.add(author.toMap()));
        return res;
    }

    @GetMapping("{id}")
    public Map<String, String> getOne(@PathVariable String id) {
        return getAuthor(id).toMap();
    }

    @PostMapping
    public Map<String, String> create(@RequestBody Author author) {
        authorService.create(author);
        return author.toMap();
    }

    @PutMapping("{id}")
    public Map<String, String> update(@PathVariable String id, @RequestBody Author author) {
        authorService.update(author, Long.parseLong(id, 10));
        return author.toMap();
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable String id) {
        authorService.delete(Long.parseLong(id, 10));
    }
}

