package ru.itfb.testproject.controllers;

import org.springframework.web.bind.annotation.*;
import ru.itfb.testproject.model.Author;
import ru.itfb.testproject.model.AuthorBook;
import ru.itfb.testproject.service.AuthorBookService;
import ru.itfb.testproject.service.AuthorService;
import ru.itfb.testproject.service.BookService;

import java.util.ArrayList;
import java.util.List;

/**
 * Контроллер для {@link Author}
 * По сути тут был готовый backend(если я правильно все понял),
 * но после было сказано сделать @Сontroller для визуализации,
 * по этому все аннотации перекочевали в {@link ru.itfb.testproject.controllers.view.ViewAuthor}
 */
@RestController
public class AuthorsController {

    private final BookService bookService;
    private final AuthorService authorService;
    private final AuthorBookService authorBookService;

    public AuthorsController(BookService bookService, AuthorService authorService, AuthorBookService authorBookService) {
        this.bookService = bookService;
        this.authorService = authorService;
        this.authorBookService = authorBookService;
    }

    /**
     * GET запрос всех авторов
     * @return массив всех авторов
     */
    //@GetMapping
    public List<Author> getAuthors() {
        return new ArrayList<>(authorService.readAll());
    }

    /**
     * GET запрос для одного автора
     * @param id уникальный идентификатор автора
     * @return автора
     */
    //@GetMapping("{id}")
    public Author getOne(@PathVariable String id) {
        return authorService.getAuthor(id);
    }

    /**
     * POST запрос для создания автора
     * @param author новый автор
     * @return нового автора
     */
    //@PostMapping("authors")
    public Author create(@RequestBody Author author) {
        author.setId(-1L);
        authorService.save(author);
        return author;
    }

    /**
     * PUT запрос для обновления
     * Изменяет автора только если он есть
     * @param id уникальный идентификатор автора
     * @param author новые данные
     * @return измененного автора
     */
    //@PutMapping("authors/{id}")
    public Author update(@PathVariable String id, @RequestBody Author author) {
        if (authorService.hasAuthor(author))
            if (authorService.getAuthorId(author) == Long.parseLong(id, 10))
                authorService.update(author, Long.parseLong(id, 10));
        return author;
    }

    /**
     * DELETE запрос
     * Удаляет автора и все его книги из БД
     * @param id уникальный идентификатор автора
     */
    //@DeleteMapping("{id}")
    public void delete(@PathVariable String id) {
        List<AuthorBook> ids_books = authorBookService.getByIdAuthor(Long.parseLong(id, 10));
        for (AuthorBook it : ids_books) {
            bookService.delete(it.getId_book());
            authorBookService.delete(it.getId());
        }
        authorService.delete(Long.parseLong(id, 10));
    }
}

