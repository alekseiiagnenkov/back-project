package ru.itfb.testproject.service;

import org.springframework.stereotype.Service;
import ru.itfb.testproject.entity.Author;
import ru.itfb.testproject.repositories.AuthorRepository;

import java.util.List;

/**
 * Service для {@link Author}
 * Тут прописывал основные функции, в которых нужна была связь с БД
 */
@Service
public class AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    /**
     * Сохранение автора в таблицу
     * @param author автор, которого нужно сохранить
     */
    public void save(Author author) {
        author.setId(-1L);
        authorRepository.save(author);
    }

    /**
     * Считывание всех авторов из БД
     * @return лист авторов
     */
    public List<Author> readAll() {
        return authorRepository.findAll();
    }

    /**
     * Обновление автора
     * @param author новые данные автора
     * @param id уникальный идентификатор, по которому будут заменены данные
     * @return true, если обновилось, false если не нашел такого
     */
    public boolean update(Author author, Long id) {
        if (hasAuthor(author))
            if (getAuthorId(author).equals(id)) {
                if (authorRepository.existsById(id)) {
                    author.setId(id);
                    authorRepository.save(author);
                    return true;
                }
            }
        return false;
    }

    /**
     * Удаление автора из БД
     * @param id уникальный идентификатор автора
     * @return true, если удалилось, false если не нашел такого
     */
    public boolean delete(Long id) {
        if (authorRepository.existsById(id)) {
            authorRepository.deleteById(id);
            return true;
        }
        return false;
    }

    /**
     * Проверяет наличие автора в таблице
     * @param author автор, которого хотим там найти
     * @return найденного автора или null
     */
    public boolean hasAuthor(Author author) {
        Author author1 = readAll().stream()
                .filter(a -> a.equals(author))
                .findFirst().orElse(null);
        return author1 != null;
    }

    /**
     * Получить id по автору
     * @param author автор, чей id нас интересует
     * @return id или null, если не найден
     */
    public Long getAuthorId(Author author) {
        Author it = readAll().stream()
                .filter(a -> a.equals(author))
                .findFirst().orElse(null);
        return it != null ? it.getId() : readAll().size();
    }

    /**
     * Получить автора по id
     * @param id интересующий нас id
     * @return автор или null, если не найден
     */
    public Author getAuthor(String id) {
        return readAll().stream()
                .filter(author -> author.getId().toString().equals(id))
                .findFirst()
                .orElse(null);
    }

}
