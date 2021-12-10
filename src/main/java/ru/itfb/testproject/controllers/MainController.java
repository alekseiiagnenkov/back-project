package ru.itfb.testproject.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.itfb.testproject.models.Book;
import ru.itfb.testproject.repositories.BookRepository;

@Controller
public class MainController {

    @GetMapping("/")
    public String main(Model model) {
        model.addAttribute("title", "glad to see you");
        return "main";
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("title", "glad to see you");
        return "login";
    }

    @GetMapping("/books")
    public String books(Model model){
        //LibraryController libraryController;
        //Iterable<Book> books = libraryController.json().findAll();
        model.addAttribute("books", "Hello");
        return "books";
    }

    @GetMapping("/authors")
    public String authors(Model model) {
        return "authors";
    }
}