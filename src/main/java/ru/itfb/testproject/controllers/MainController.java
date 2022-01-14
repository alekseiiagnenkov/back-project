package ru.itfb.testproject.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Контроллер для страниц about и main
 */
@Controller
public class MainController {

    @GetMapping("/about")
    public String about() {
        return "about";
    }

    @GetMapping("/")
    public String main() {
        return "main";
    }

}