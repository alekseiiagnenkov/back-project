package ru.itfb.testproject.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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
        return "books";
    }

    @GetMapping("/authors")
    public String authors(Model model) {
        return "authors";
    }
}