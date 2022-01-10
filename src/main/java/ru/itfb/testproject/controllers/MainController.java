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
        log.info("Open about");
        return "about";
    }

    @GetMapping("/")
    public String main() {
        log.info("Open main");
        return "main";
    }

}