package ru.itfb.testproject.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.itfb.testproject.entities.Book;
import ru.itfb.testproject.repositories.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;

@RestController
public class BookController {

    private final static Logger logger = LoggerFactory.getLogger(BookController.class);

    private BookRepository bookRepository;

    @Autowired
    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @RequestMapping("json")
    public void json(){
        URL url = this.getClass().getClassLoader().getResource("books.json");

        if(url != null){

            File jsonFile = new File(url.getFile());

            ObjectMapper objectMapper = new ObjectMapper();

            try {
                List<Book> books = objectMapper.readValue(jsonFile, new TypeReference<List<Book>>(){

                });

                bookRepository.saveAll(books);

                logger.info("All records saved.");

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            logger.warn("uri is null.");
        }

    }

    @GetMapping("/books")
    public String books(Model model){
        return "books";
    }

}
