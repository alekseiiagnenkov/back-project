package ru.itfb.backproject.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MainController {

    /**
     * Приветствие
     * @return welcome.html
     */
    @RequestMapping( method = RequestMethod.GET)
    public String main() {
        return "welcome";
    }
}