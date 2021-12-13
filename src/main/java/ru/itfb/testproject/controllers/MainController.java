package ru.itfb.testproject.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.itfb.testproject.models.Author;
import ru.itfb.testproject.models.AuthorBook;
import ru.itfb.testproject.models.Book;
import ru.itfb.testproject.repositories.AuthorBookRepository;
import ru.itfb.testproject.repositories.AuthorRepository;
import ru.itfb.testproject.repositories.BookRepository;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

@Controller
public class MainController {

    private final static Logger logger = LoggerFactory.getLogger(MainController.class);

    private BookRepository bookRepository;
    private AuthorRepository authorRepository;
    private AuthorBookRepository authorBookRepository;

    @Autowired
    public MainController(BookRepository bookRepository, AuthorRepository authorRepository, AuthorBookRepository authorBookRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.authorBookRepository = authorBookRepository;
        jsonBooks();
        jsonAuthors();
        jsonAuthorBook();
    }

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
    @RequestMapping(value = "/toBook", method = RequestMethod.POST)
    public String book(Model model, @RequestParam(name = "Pk") Long Pk){
        Iterator<Book> books = bookRepository.findAll().iterator();
        Book book = books.next();
        while (!Objects.equals(book.getPk(), Pk)){
            book = books.next();
        }
        model.addAttribute("book", book);
        return "book";
    }

    @GetMapping("/author")
    @RequestMapping(value = "/toAuthor", method = RequestMethod.POST)
    public String author(Model model, @RequestParam(name = "Pk") Long Pk){
        Iterator<Author> authors = authorRepository.findAll().iterator();
        Author author = authors.next();
        while (!Objects.equals(author.getPk(), Pk)){
            author = authors.next();
        }
        model.addAttribute("author", author);
        return "author";
    }

    @GetMapping("/authors")
    public String authors(Model model) {
        Iterable<Author> authors = authorRepository.findAll();
        model.addAttribute("authors", authors);
        return "authors";
    }

    @RequestMapping("jsonAuthors")
    @ResponseBody
    public void jsonAuthors() {
        URL url = this.getClass().getClassLoader().getResource("authors.json");

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
    }

    @RequestMapping("jsonAuthorBook")
    @ResponseBody
    public void jsonAuthorBook() {
        URL url = this.getClass().getClassLoader().getResource("author_book.json");

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

    @RequestMapping("jsonBooks")
    @ResponseBody
    public void jsonBooks(){
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


    }
}