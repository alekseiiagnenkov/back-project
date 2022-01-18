package ru.itfb.testproject.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.itfb.testproject.entity.Author;
import ru.itfb.testproject.entity.AuthorBook;
import ru.itfb.testproject.service.AuthorBookService;
import ru.itfb.testproject.service.AuthorService;
import ru.itfb.testproject.service.BookService;

import javax.servlet.http.HttpServletRequest;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Контроллер для {@link Author}
 */
@Controller
@RequestMapping("authors")
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
     * Визуализация GET запроса для всех авторов
     * @param model для передачи параметров в html страницу для их отображения
     * @return файл отображения authors.html
     */
    @RequestMapping (method = RequestMethod.GET)
    public String getAuthors(Model model) {
        model.addAttribute("authors", authorService.readAll());
        return "authors";
    }

    /**
     * визуализация GET запроса для определенного автора
     * @param id уникальный идентификатор нашего автора
     * @param model для передачи параметров в html страницу для их отображения
     * @return файл отображения author.html
     */
    @RequestMapping (value = "{id}", method = RequestMethod.GET)
    public String getOne(@PathVariable String id, Model model) {
        Author author = authorService.getAuthor(id);
        model.addAttribute("author", author);
        model.addAttribute("model", model);
        model.addAttribute("view", this);
        return "author";
    }

    /**
     * POST запрос, визуализации нет, не успел
     * @param author новый автор
     * @return нового автора
     */
    @RequestMapping ( method = RequestMethod.POST)
    public Author create(@RequestBody Author author) {
        author.setId(-1L);
        authorService.save(author);
        return author;
    }

    /**
     * PUT запрос, визуализации нет, не успел
     * @param id уникальный идентификатор автора, которого будем менять
     * @param author новые данные этого автора
     * @return измененного автора
     */
    @RequestMapping (value = "{id}", method = RequestMethod.PUT)
    public Author update(@PathVariable String id, @RequestBody Author author) {
        if (authorService.hasAuthor(author))
            if (authorService.getAuthorId(author) == Long.parseLong(id, 10))
                authorService.update(author, Long.parseLong(id, 10));
        return author;
    }

    /**
     * DELETE запрос для удаления автора из БД
     * После удаления переносит в /authors
     * @param id уникальный идентификатор автора, которого будем удалять
     * @param request необходим для возврата на предыдущие страницы после удаления автора
     * @return переход на страницу со всеми авторами
     */
    @RequestMapping (value = "{id}", method = RequestMethod.DELETE)
    public String delete(@PathVariable String id, HttpServletRequest request) {
        List<AuthorBook> ids_books = authorBookService.getByIdAuthor(Long.parseLong(id, 10));
        for (AuthorBook it : ids_books) {
            bookService.delete(it.getIdBook());
            authorBookService.delete(it.getId());
        }
        authorService.delete(Long.parseLong(id, 10));
        Path link = Paths.get(request.getHeader("Referer")).getParent();
        return "redirect:/"+ link;
    }
}

