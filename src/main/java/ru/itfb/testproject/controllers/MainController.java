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

    @GetMapping("/about")
    public String about(Model model) {
        model.addAttribute("");
        return "about";
    }

    @GetMapping("/authors")
    public String authors(Model model) {
        model.addAttribute("");
        return "authors";
    }
}