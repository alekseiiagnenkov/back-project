package ru.itfb.testproject.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
public class MainController {

    @GetMapping("/about")
    public String about(Model model) {
        return "about";
    }

    @GetMapping("/")
    public String main(Model model) {
        return "main";
    }

}