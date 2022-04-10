package ru.itfb.backproject.service;

import org.springframework.stereotype.Service;
import ru.itfb.backproject.entity.AuthorBook;
import ru.itfb.backproject.repositories.AuthorBookRepository;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Service для {@link AuthorBook}
 * Тут прописывал основные функции, в которых нужна была связь с БД
 */
@Service
public class AuthorBookService {

    private final AuthorBookRepository authorBookRepository;

    public AuthorBookService(AuthorBookRepository authorBookRepository) {
        this.authorBookRepository = authorBookRepository;
    }

    /**
     * Сохранение связи в таблицу
     * @param authorBook связь, которого нужно сохранить
     */
    public void save(AuthorBook authorBook) {
        authorBookRepository.save(authorBook);
    }

    /**
     * Считывание всех связей между автором и книгами из БД
     * @return лист связей
     */
    public List<AuthorBook> readAll() {
        return authorBookRepository.findAll();
    }

    /**
     * Обновление связи
     * @param authorBook новые данные связи
     * @param id уникальный идентификатор, по которому будут заменены данные
     * @return true, если обновилось, false если не нашел такого
     */
    public boolean update(AuthorBook authorBook, Long id) {
        if (authorBookRepository.existsById(id)) {
            authorBook.setId(id);
            authorBookRepository.save(authorBook);
            return true;
        }
        return false;
    }

    /**
     * Удаление связи из БД
     * @param id уникальный идентификатор связи
     * @return true, если удалилось, false если не нашел такого
     */
    public boolean delete(Long id) {
        if (authorBookRepository.existsById(id)) {
            authorBookRepository.deleteById(id);
            return true;
        }
        return false;
    }

    /**
     * Получить связи по id автора
     * @param id_author id автора
     * @return связи
     */
    public List<AuthorBook> getByIdAuthor(Long id_author){
        return authorBookRepository.findAll().stream()
                .filter(ab -> Objects.equals(ab.getIdAuthor(), id_author))
                .collect(Collectors.toList());
    }

    /**
     * Получить связь по id книги
     * @param id_book id книги
     * @return связь или null
     */
    public AuthorBook getByIdBook(Long id_book) {
        return authorBookRepository.findAll().stream()
                .filter(id_b -> id_b.getIdBook().equals(id_book))
                .findFirst().orElse(null);
    }
}
