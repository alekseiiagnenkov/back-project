package ru.itfb.testproject.service;

import org.springframework.stereotype.Service;
import ru.itfb.testproject.model.Person;
import ru.itfb.testproject.repositories.PersonRepository;

import java.util.List;

/**
 * Service для {@link Person}
 * Тут прописывал основные функции, в которых нужна была связь с БД
 */
@Service
public class PersonService {

    private final PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    /**
     * Сохранение пользователя в таблицу
     * @param person пользователь, которого нужно сохранить
     */
    public void save(Person person) {
        personRepository.save(person);
    }

    /**
     * Считывание всех пользователей из БД
     * @return лист пользователей
     */
    public List<Person> readAll() {
        return personRepository.findAll();
    }

    /**
     * Обновление пользователя
     * @param person новые данные пользователя
     * @param id уникальный идентификатор, по которому будут заменены данные
     * @return true, если обновилось, false если не нашел такого
     */
    public boolean update(Person person, Long id) {
        if (personRepository.existsById(id)) {
            person.setId(id);
            personRepository.save(person);
            return true;
        }
        return false;
    }

    /**
     * Удаление пользователя из БД
     * @param id уникальный идентификатор пользователя
     * @return true, если удалилось, false если не нашел такого
     */
    public boolean delete(Long id) {
        if (personRepository.existsById(id)) {
            personRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
