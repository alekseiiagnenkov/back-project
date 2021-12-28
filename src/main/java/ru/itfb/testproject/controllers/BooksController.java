package ru.itfb.testproject.controllers;

import org.springframework.web.bind.annotation.*;
import ru.itfb.testproject.exceptions.BookNotFound;
import ru.itfb.testproject.model.*;
import ru.itfb.testproject.service.AuthorBookService;
import ru.itfb.testproject.service.AuthorService;
import ru.itfb.testproject.service.BookService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("books")
public class BooksController {

    private final BookService bookService;
    private final AuthorService authorService;
    private final AuthorBookService authorBookService;


    public BooksController(BookService bookService, AuthorService authorService, AuthorBookService authorBookService) {
        this.bookService = bookService;
        this.authorService = authorService;
        this.authorBookService = authorBookService;
    }

    @GetMapping
    public List<Map<String, String>> books() {
        List<Map<String, String>> books = new ArrayList<>();
        bookService.readAll().forEach(book -> books.add(book.toMap()));
        return books;
    }

    @GetMapping("{id}")
    public Map<String, String> getOne(@PathVariable String id) {
        return bookService.getBook(id).toMap();
    }

    @PostMapping
    public Book create(@RequestBody AuthorBookDTO authorBookDTO) {
        if (!bookService.hasBook(authorBookDTO.getBook()) ) {
            authorBookDTO.getBook().setId(-1L); //TODO или тут лучще проверять на то, свободно ли там?
            authorBookDTO.getAuthor().setId(-1L);//TODO или тут лучще проверять на то, свободно ли там?
            bookService.create(authorBookDTO.getBook());
            if (!authorService.hasAuthor(authorBookDTO.getAuthor()))
                authorService.create(authorBookDTO.getAuthor());
            AuthorBook ab = new AuthorBook(-1L, authorService.getAuthorId(authorBookDTO.getAuthor()), bookService.getLastBook().getId()); //TODO или тут лучще проверять на то, свободно ли там?
            authorBookService.create(ab);
        }
        return authorBookDTO.getBook();
    }

    @PutMapping("{id}") // TODO что тут апдейтить
    public Map<String, String> update(@PathVariable String id, @RequestBody Book book) {
        bookService.update(book, Long.parseLong(id, 10));
        return book.toMap();
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable String id) {
        authorBookService.delete(authorBookService.getIdByIDBook(Long.parseLong(id, 10)).getId());
        bookService.delete(Long.parseLong(id, 10));
    }
}