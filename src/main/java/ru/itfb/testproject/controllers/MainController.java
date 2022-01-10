package ru.itfb.testproject.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Контроллер для страниц about и main
 */
@Slf4j
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