package ru.itfb.testproject.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.itfb.testproject.models.Author;
import ru.itfb.testproject.models.AuthorBook;
import ru.itfb.testproject.models.Book;
import ru.itfb.testproject.repositories.AuthorBookRepository;
import ru.itfb.testproject.repositories.AuthorRepository;
import ru.itfb.testproject.repositories.BookRepository;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;

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
        Iterable<Book> books = bookRepository.findAll();
        model.addAttribute("books", books);
        return "books";
    }

    @GetMapping("/book")
    public String book(Model model){
        Iterable<Book> book = bookRepository.findAll();
        model.addAttribute("book", book);
        return "book";
    }

    @GetMapping("/authors")
    public String authors(Model model) {
        return "authors";
    }






    private final static Logger logger = LoggerFactory.getLogger(MainController.class);

    private BookRepository bookRepository;
    private AuthorRepository authorRepository;
    private AuthorBookRepository authorBookRepository;

    @Autowired
    public MainController(BookRepository bookRepository, AuthorRepository authorRepository, AuthorBookRepository authorBookRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.authorBookRepository = authorBookRepository;
    }

    @RequestMapping("json")
    @ResponseBody
    public void json(){
        URL url = this.getClass().getClassLoader().getResource("books.json");

        if(url != null){

            File jsonFile = new File(url.getFile());

            ObjectMapper objectMapper = new ObjectMapper();

            try {
                List<Book> books = objectMapper.readValue(jsonFile, new TypeReference<>(){

                });

                bookRepository.saveAll(books);

                logger.info("All records saved.");

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            logger.warn("uri is null.");
        }



        url = this.getClass().getClassLoader().getResource("authors.json");

        if(url != null){

            File jsonFile = new File(url.getFile());

            ObjectMapper objectMapper = new ObjectMapper();

            try {
                List<Author> authors = objectMapper.readValue(jsonFile, new TypeReference<>(){

                });

                authorRepository.saveAll(authors);

                logger.info("All records saved.");

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            logger.warn("uri is null.");
        }


        url = this.getClass().getClassLoader().getResource("author_book.json");

        if(url != null){

            File jsonFile = new File(url.getFile());

            ObjectMapper objectMapper = new ObjectMapper();

            try {
                List<AuthorBook> authorBook = objectMapper.readValue(jsonFile, new TypeReference<>(){

                });

                authorBookRepository.saveAll(authorBook);

                logger.info("All records saved.");

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            logger.warn("uri is null.");
        }
    }
}