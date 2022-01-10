package ru.itfb.testproject.service;

import org.springframework.stereotype.Service;
import ru.itfb.testproject.exceptions.BookNotFound;
import ru.itfb.testproject.model.Book;
import ru.itfb.testproject.repositories.BookRepository;

import java.util.List;

/**
 * Service для {@link Book}
 * Тут прописывал основные функции, в которых нужна была связь с БД
 */
@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    /**
     * Сохранение книги в таблицу
     * @param book книга, которого нужно сохранить
     */
    public void save(Book book) {
        bookRepository.save(book);
    }

    /**
     * Считывание всех книг из БД
     * @return лист книг
     */
    public List<Book> readAll(){
        return bookRepository.findAll();
    }

    /**
     * Обновление книги
     * @param book новые данные книги
     * @param id уникальный идентификатор, по которому будут заменены данные
     * @return true, если обновилось, false если не нашел такого
     */
    public boolean update(Book book, Long id) {
        if (bookRepository.existsById(id)) {
            book.setId(id);
            bookRepository.save(book);
            return true;
        }

        return false;
    }

    /**
     * Удаление книги из БД
     * @param id уникальный идентификатор книги
     * @return true, если удалилось, false если не нашел такого
     */
    public boolean delete(Long id) {
        if (bookRepository.existsById(id)) {
            bookRepository.deleteById(id);
            return true;
        }
        return false;
    }

    /**
     * Проверяет наличие книги в таблице
     * @param book книга, которую хотим там найти
     * @return найденную книгу или null
     */
    public boolean hasBook(Book book) {
        Book book1 = readAll().stream()
                .filter(b -> b.getName().equals(book.getName()))
                .findFirst().orElse(null);
        return book1 != null;
    }

    /**
     * Получить последнюю добавленную книгу
     * Нужно для создания связи между книгой и
     * автором(чтобы узнать реальный id только что добавленной книги)
     * @return последнюю добавленную книгу
     */
    public Book getLastBook() {
        return readAll().get(readAll().size() - 1);
    }

    /**
     * Получить книгу по id
     * @param id интересующий нас id
     * @return книга или null, если не найдена
     */
    public Book getBook(String id) throws BookNotFound {
        return readAll().stream()
                .filter(book -> book.getId().toString().equals(id))
                .findFirst()
                .orElseThrow(BookNotFound::new);
    }

}
