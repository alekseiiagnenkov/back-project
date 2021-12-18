package ru.itfb.testproject.controllers;

import org.springframework.web.bind.annotation.*;
import ru.itfb.testproject.exceptions.NotFoundException;
import ru.itfb.testproject.model.Author;
import ru.itfb.testproject.service.AuthorService;
import ru.itfb.testproject.service.BookService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("authorsJSON")
public class AuthorsController {

    private int counter = 1;
    private AuthorService authorService;

    public AuthorsController(AuthorService authorService) {
        this.authorService = authorService;
    }

    private Author getAuthor(String id) {
        return authorService.readAll().stream()
                .filter(author -> author.getId().toString().equals(id))
                .findFirst()
                .orElseThrow(NotFoundException::new);
    }

    @GetMapping
    public List<Map<String, String>> getAuthors() {
        List<Map<String,String>> res = new ArrayList<>();
        authorService.readAll().forEach(author -> res.add(author.toMap()));
        return res;
    }

    @GetMapping("{id}")
    public Map<String, String> getOne(@PathVariable String id) {
        return getAuthor(id).toMap();
    }

/*    @PostMapping
    public Map<String, String> create(@RequestBody Map<String, String> author) {
        author.put("id", String.valueOf(counter++));
        authors.add(author);
        return author;
    }

    @PutMapping("{id}")
    public Map<String, String> update(@PathVariable String id, @RequestBody Map<String, String> author) {
        Map<String, String> authorFromDB = getAuthor(id);

        authorFromDB.putAll(author);
        authorFromDB.put("id", id);
        return authorFromDB;
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable String id) {
        Map<String, String> author = getAuthor("id");
        authors.remove(author);
    }*/
}

