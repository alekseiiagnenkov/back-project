package ru.itfb.testproject.controllers;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.itfb.testproject.entity.Author;
import ru.itfb.testproject.entity.AuthorBook;
import ru.itfb.testproject.entity.dto.AuthorBookDTO;
import ru.itfb.testproject.entity.Book;
import ru.itfb.testproject.exceptions.BookNotFound;
import ru.itfb.testproject.mappers.AuthorMapper;
import ru.itfb.testproject.mappers.BookMapper;
import ru.itfb.testproject.service.AuthorBookService;
import ru.itfb.testproject.service.AuthorService;
import ru.itfb.testproject.service.BookService;

import javax.servlet.http.HttpServletRequest;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;


/**
 * Контроллер для {@link Book}
 */
@Controller
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

    /**
     * Визуализация GET запроса для всех книг
     *
     * @param model для передачи параметров в html страницу для их отображения
     * @return файл отображения books.html
     */
    @RequestMapping(method = RequestMethod.GET)
    public String getBooks(Model model) {
        model.addAttribute("books", bookService.readAll());
        return "books";
    }

    /**
     * Визуализация GET запроса для определенной книги
     * Отображает так же автора книги
     * если книга не найдена, то возвращаемся на страницу со всеми книгами
     *
     * @param id    уникальный идентификатор нашей книги
     * @param model для передачи параметров в html страницу для их отображения
     * @return файл отображения book.html
     */
    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public String getOne(@PathVariable String id, Model model) throws BookNotFound {
        Book book = bookService.getBook(id);
        Author author = authorService.getAuthor(book.getId().toString());
        model.addAttribute("author", author);
        model.addAttribute("book", book);
        model.addAttribute("model", model);
        model.addAttribute("view", this);
        return "book";
    }

    /**
     * POST запрос для создания книги
     * (Обязательно с книгой нужно указывать автора)
     * <p>
     * Если автор уже есть, то она к нему прикрепится.
     * Если нет, то создается новый автор и книга
     *
     * @param authorBookDTO автор+книга
     * @return автор+книга
     */
    @RequestMapping(method = RequestMethod.POST)
    public Book create(@RequestBody AuthorBookDTO authorBookDTO) {
        Book book = BookMapper.dtoToEntity(authorBookDTO);
        Author author = AuthorMapper.dtoToEntity(authorBookDTO);
        if (!bookService.hasBook(book)) {
            bookService.save(book);
            if (!authorService.hasAuthor(author))
                authorService.save(author);
            AuthorBook ab = new AuthorBook()
                    .setId(-1L)
                    .setIdAuthor(authorService.getAuthorId(author))
                    .setIdBook(bookService.getLastBook().getId());
            authorBookService.save(ab);
            return bookService.getLastBook();
        }
        return null;
    }

    /**
     * PUT запрос для обновления
     * Изменяет книгу только если она есть
     *
     * @param id   уникальный идентификатор книги
     * @param book новые данные
     * @return измененную книгу
     */
    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public Book update(@PathVariable String id, @RequestBody Book book) throws BookNotFound {
        bookService.update(book, Long.parseLong(id, 10));
        return bookService.getBook(id);
    }

    /**
     * Получает автора по id книги
     *
     * @param id уникальный идентификатор книги
     * @return автора, которому принадлежит эта книга
     */
    public Author getAuthor(Long id) {
        return authorService.getAuthor(authorBookService.getByIdBook(id).getIdAuthor().toString());
    }

    /**
     * DELETE запрос
     * Удаляет книгу и связь между ней и автором
     * <p>
     * После удаления переносит в /authors
     *
     * @param id      уникальный идентификатор книги, которую будем удалять
     * @param request необходим для возврата на предыдущие страницы после удаления книги
     * @return переход на страницу со всеми книгами
     */
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public String delete(@PathVariable String id, HttpServletRequest request) {
        authorBookService.delete(authorBookService.getByIdBook(Long.parseLong(id, 10)).getId());
        bookService.delete(Long.parseLong(id, 10));
        Path link = Paths.get(request.getHeader("Referer")).getParent();
        return "redirect:/" + link;
    }

    /**
     * GET запрос
     * Поиск всех книг по части фамилии автора
     *
     * @param someText часть фамилии автора
     * @return все книги, у которых в фамилии автора есть someText
     */
    @GetMapping("/findbooks")
    public List findbooks(@RequestParam(value = "sometext") String someText) {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure() // configures settings from hibernate.cfg.xml
                .build();
        SessionFactory sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        Session session = sessionFactory.openSession();

        Query query = session.createQuery(
                "SELECT b, a FROM Book AS b " +
                        "LEFT JOIN AuthorBook AS ab ON b.id = ab.idBook " +
                        "LEFT JOIN Author AS a ON a.id = ab.idAuthor " +
                        "WHERE a.lastName LIKE '%" + someText + "%'");

        return query.list();
    }
}