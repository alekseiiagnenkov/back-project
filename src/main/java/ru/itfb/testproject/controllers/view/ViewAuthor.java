package ru.itfb.testproject.controllers.view;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.itfb.testproject.controllers.AuthorsController;
import ru.itfb.testproject.model.Author;

import javax.servlet.http.HttpServletRequest;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Создан для визуализации работы приложения с классом {@link Author}
 * Не реализована визуализация для: создания и обновления
 */
@Controller
@RequestMapping("authors")
public class ViewAuthor {

    private final AuthorsController authorsController;

    public ViewAuthor(AuthorsController authorsController) {
        this.authorsController = authorsController;
    }

    /**
     * Визуализация GET запроса для всех авторов
     * @param model для передачи параметров в html страницу для их отображения
     * @return файл отображения authors.html
     */
    @RequestMapping (method = RequestMethod.GET)
    public String getAuthors(Model model) {
        model.addAttribute("authors", authorsController.getAuthors());
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
        Author author = authorsController.getOne(id);
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
        authorsController.create(author);
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
        authorsController.update(id, author);
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
        authorsController.delete(id);
        Path link = Paths.get(request.getHeader("Referer")).getParent();
        return "redirect:"+ link;
    }
}
