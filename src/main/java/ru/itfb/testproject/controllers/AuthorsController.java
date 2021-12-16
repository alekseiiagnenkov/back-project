package ru.itfb.testproject.controllers;

import org.springframework.web.bind.annotation.*;
import ru.itfb.testproject.exceptions.NotFoundException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("authorsJSON")
public class AuthorsController {

    private int counter = 4;
    private List<Map<String, String>> authors = new ArrayList<>() {{
        add(new HashMap<>() {{
            put("id", "1");
            put("firstName", "Lev");
            put("lastName", "Tolstoy");
        }});
        add(new HashMap<>() {{
            put("id", "2");
            put("firstName", "Evgeny");
            put("lastName", "Petrov");
        }});
        add(new HashMap<>() {{
            put("id", "3");
            put("firstName", "Ilya");
            put("lastName", "Ilf");
        }});
    }};

    private Map<String, String> getAuthor(String id) {
        return authors.stream()
                .filter(author -> author.get("id").equals(id))
                .findFirst()
                .orElseThrow(NotFoundException::new);
    }

    @GetMapping
    public List<Map<String, String>> getAuthors() {
        return authors;
    }

    @GetMapping("{id}")
    public Map<String, String> getOne(@PathVariable String id) {
        return getAuthor(id);
    }

    @PostMapping
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
    }
}

