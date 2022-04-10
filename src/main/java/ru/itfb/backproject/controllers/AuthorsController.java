package ru.itfb.backproject.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.itfb.backproject.entity.Author;
import ru.itfb.backproject.service.AuthorService;

import javax.servlet.http.HttpServletRequest;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Контроллер для {@link Author}
 */
@Controller
@RequestMapping("authors")
public class AuthorsController {

    private final AuthorService authorService;

    public AuthorsController(AuthorService authorService) {
        this.authorService = authorService;
    }

    /**
     * Визуализация GET запроса для всех авторов
     *
     * @param model для передачи параметров в html страницу для их отображения
     * @return файл отображения authors.html
     */
    @RequestMapping(method = RequestMethod.GET)
    public String getAuthors(Model model) {
        model.addAttribute("authors", authorService.readAll());
        return "authors";
    }

    /**
     * Визуализация GET запроса для определенного автора
     *
     * @param id    уникальный идентификатор нашего автора
     * @param model для передачи параметров в html страницу для их отображения
     * @return файл отображения author.html
     */
    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public String getOne(@PathVariable String id, Model model) {
        model.addAttribute("author", authorService.getAuthor(id));
        model.addAttribute("model", model);
        model.addAttribute("view", this);
        return "author";
    }

    /**
     * POST запрос, визуализации нет, не успел
     *
     * @param author новый автор
     * @return нового автора
     */
    @RequestMapping(method = RequestMethod.POST)
    public Author create(@RequestBody Author author) {
        authorService.save(author);
        return author;
    }

    /**
     * PUT запрос, визуализации нет, не успел
     *
     * @param id     уникальный идентификатор автора, которого будем менять
     * @param author новые данные этого автора
     * @return измененного автора или null
     */
    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public boolean update(@PathVariable String id, @RequestBody Author author) {
        return authorService.update(author, Long.parseLong(id, 10));
    }

    /**
     * DELETE запрос для удаления автора из БД
     * После удаления переносит в /authors
     *
     * @param id      уникальный идентификатор автора, которого будем удалять
     * @param request необходим для возврата на предыдущие страницы после удаления автора
     * @return переход на страницу со всеми авторами
     */
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public String delete(@PathVariable String id, HttpServletRequest request) {
        authorService.delete(Long.parseLong(id, 10));

        Path link = Paths.get(request.getHeader("Referer")).getParent();
        return "redirect:/" + link;
    }
}

