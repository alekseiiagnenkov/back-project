package ru.itfb.testproject.controllers;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;
import org.springframework.web.bind.annotation.*;
import ru.itfb.testproject.exceptions.BookNotFound;
import ru.itfb.testproject.model.Author;
import ru.itfb.testproject.model.AuthorBook;
import ru.itfb.testproject.model.AuthorBookDTO;
import ru.itfb.testproject.model.Book;
import ru.itfb.testproject.service.AuthorBookService;
import ru.itfb.testproject.service.AuthorService;
import ru.itfb.testproject.service.BookService;

import java.util.ArrayList;
import java.util.List;


/**
 * Контроллер для {@link Book}
 * По сути тут был готовый backend(если я правильно все понял),
 * но после было сказано сделать @Сontroller для визуализации,
 * по этому все аннотации перекочевали в {@link ru.itfb.testproject.controllers.view.ViewBook}
 */
@Slf4j
@RestController
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
     * GET запрос всех книг
     * @return массив всех книг
     */
    //@GetMapping
    public List<Book> getBooks() {
        return new ArrayList<>(bookService.readAll());
    }

    /**
     * GET запрос для одной книги
     * @param id уникальный идентификатор книги
     * @return книгу
     */
    //@GetMapping("{id}")
    public Book getOne(@PathVariable String id) throws BookNotFound {
        return bookService.getBook(id);
    }

    /**
     * POST запрос для создания книги
     * (Обязательно с книгой нужно указывать автора)
     *
     * Если автор уже есть, то она к нему прикрепится.
     * Если нет, то создается новый автор и книга
     * @param authorBookDTO автор+книга
     * @return автор+книга
     */
    //@PostMapping("books")
    public Book create(@RequestBody AuthorBookDTO authorBookDTO) {
        if (!bookService.hasBook(authorBookDTO.getBook())) {
            authorBookDTO.getBook().setId(-1L); //TODO или тут нужно проверять на то, свободно ли там?
            authorBookDTO.getAuthor().setId(-1L);//TODO или тут нужно проверять на то, свободно ли там?
            bookService.save(authorBookDTO.getBook());
            if (!authorService.hasAuthor(authorBookDTO.getAuthor()))
                authorService.save(authorBookDTO.getAuthor());
            AuthorBook ab = new AuthorBook(-1L, authorService.getAuthorId(authorBookDTO.getAuthor()), bookService.getLastBook().getId()); //TODO или тут нужно проверять на то, свободно ли там?
            authorBookService.save(ab);
        }
        return authorBookDTO.getBook();
    }

    /**
     * PUT запрос для обновления
     * Изменяет книгу только если она есть
     * @param id уникальный идентификатор книги
     * @param book новые данные
     * @return измененную книгу
     */
    //@PutMapping("books/{id}")
    public Book update(@PathVariable String id, @RequestBody Book book) {
        bookService.update(book, Long.parseLong(id, 10));
        return book;
    }

    /**
     * Получает автора по id книги
     * @param id уникальный идентификатор книги
     * @return автора, которому принадлежит эта книга
     */
    public Author getAuthor(Long id){
        return authorService.getAuthor(authorBookService.getByIdBook(id).getId_author().toString());
    }

    /**
     * DELETE запрос
     * Удаляет книгу и связь между ней и автором
     * @param id уникальный идентификатор книги
     */
    //@DeleteMapping("{id}")
    public void delete(@PathVariable String id) {
        authorBookService.delete(authorBookService.getByIdBook(Long.parseLong(id, 10)).getId());
        bookService.delete(Long.parseLong(id, 10));
    }

    /**
     * GET запрос
     * Поиск всех книг по части фамилии автора
     * @param someText часть фамилии автора
     * @return все книги, у которых в фамилии автора есть someText
     */
    @GetMapping("/findbooks")
    public List findbooks(@RequestParam(value = "sometext") String someText) {
        log.info("Using findbooks with sometext = "+someText);
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure() // configures settings from hibernate.cfg.xml
                .build();
        SessionFactory sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        Session session = sessionFactory.openSession();

        Query query = session.createQuery(
                "SELECT b, a FROM Book AS b " +
                        "LEFT JOIN AuthorBook AS ab ON b.id = ab.id_book " +
                        "LEFT JOIN Author AS a ON a.id = ab.id_author " +
                        "WHERE a.lastName LIKE '%"+someText+"%'");

        return query.list();
    }
}