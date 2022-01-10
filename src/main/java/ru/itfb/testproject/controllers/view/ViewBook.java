package ru.itfb.testproject.controllers.view;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.itfb.testproject.controllers.BooksController;
import ru.itfb.testproject.model.Author;
import ru.itfb.testproject.model.AuthorBookDTO;
import ru.itfb.testproject.model.Book;

import javax.servlet.http.HttpServletRequest;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Создан для визуализации работы приложения с классом {@link Book}
 * Не реализована визуализация для: создания и обновления
 */
@Slf4j
@Controller
@RequestMapping("books")
public class ViewBook {

    private final BooksController booksController;

    public ViewBook(BooksController booksController) {
        this.booksController = booksController;
    }

    /**
     * Визуализация GET запроса для всех книг
     * @param model для передачи параметров в html страницу для их отображения
     * @return файл отображения books.html
     */
    @RequestMapping(method = RequestMethod.GET)
    public String getBooks(Model model) {
        log.info("Using getBooks");
        model.addAttribute("books", booksController.getBooks());
        return "books";
    }

    /**
     * Визуализация GET запроса для определенной книги
     * Отображает так же автора книги
     * если книга не найдена, то возвращаемся на страницу со всеми книгами
     * @param id уникальный идентификатор нашей книги
     * @param model для передачи параметров в html страницу для их отображения
     * @return файл отображения book.html
     */
    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public String getOne(@PathVariable String id, Model model, HttpServletRequest request) {
        try {
            Book book = booksController.getOne(id);
            Author author = booksController.getAuthor(book.getId());
            log.info("Using getOne for " + book);
            model.addAttribute("author", author);
            model.addAttribute("book", book);
            model.addAttribute("model", model);
            model.addAttribute("view", this);
            return "book";
        } catch (Exception e) {
            Path link = Paths.get(request.getHeader("Referer")).getParent();
            System.err.print(e);
            return "redirect:" + link;
        }
    }

    /**
     * POST запрос, визуализации нет, не успел
     * @param authorBookDTO новый автор+книга
     * @return автор+книга
     */
    @RequestMapping(method = RequestMethod.POST)
    public AuthorBookDTO create(@RequestBody AuthorBookDTO authorBookDTO) {
        log.info("Using create for "+ authorBookDTO);
        booksController.create(authorBookDTO);
        return authorBookDTO;
    }

    /**
     * PUT запрос, визуализации нет, не успел
     * @param id уникальный идентификатор книги, которую будем менять
     * @param book новые данные книги
     * @return измененную книгу
     */
    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public Book update(@PathVariable String id, @RequestBody Book book) {
        log.info("Using update for id = "+id+" to "+book);
        booksController.update(id, book);
        return book;
    }

    /**
     * DELETE запрос для удаления книги из БД
     * После удаления переносит в /authors
     * @param id уникальный идентификатор книги, которую будем удалять
     * @param request необходим для возврата на предыдущие страницы после удаления книги
     * @return переход на страницу со всеми книгами
     */
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public String delete(@PathVariable String id, HttpServletRequest request) {
        log.info("Using delete book for id = " + id);
        booksController.delete(id);
        Path link = Paths.get(request.getHeader("Referer")).getParent();
        return "redirect:" + link;
    }
}

