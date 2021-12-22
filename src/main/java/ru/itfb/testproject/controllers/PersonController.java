package ru.itfb.testproject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.itfb.testproject.model.Person;
import ru.itfb.testproject.service.PersonService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("users")
public class PersonController {

    private int counter = 1;

    private final PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
        personService.readAll().forEach(person -> counter++);
    }

    @GetMapping
    public List<Map<String, String>> persons() {
        List<Map<String, String>> AllPersons = new ArrayList<>();
        personService.readAll().forEach(person -> AllPersons.add(person.toMap()));
        return AllPersons;
    }

    public Person getOne(String id){
        return personService.readAll().stream()
                .filter(person->person.getId() == Long.parseLong(id, 10))
                .findFirst()
                .orElse(null);
    }

    @GetMapping("{id}")
    public Person getPerson(@PathVariable String id) {
        Person p = getOne(id);
        return getOne(id);
    }

    @PostMapping
    public Map<String, String> create(@RequestBody Person person) {
        personService.create(person);
        return person.toMap();
    }


    @PutMapping("{id}")
    public Map<String, String> update(@PathVariable String id, @RequestBody Person person) {
        personService.update(person, Long.parseLong(id, 10));
        return person.toMap();
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable String id) {
        personService.delete(Long.parseLong(id, 10));
    }
}
