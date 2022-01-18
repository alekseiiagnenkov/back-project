package ru.itfb.testproject.service;

import org.springframework.stereotype.Service;
import ru.itfb.testproject.entity.Person;
import ru.itfb.testproject.entity.Role;
import ru.itfb.testproject.repositories.PersonRepository;

import java.util.List;

/**
 * Service для {@link Person}
 * Тут прописывал основные функции, в которых нужна была связь с БД
 */
@Service
public class PersonService{

    private PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public Person findPersonByUsername(String username){
        return personRepository.findUserByUsername(username);
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
     * Считывание пользователя из БД по id
     * @param id уникальный идентификатор пользователя
     * @return пользователя
     */
    public Person getOne(String id){
        return personRepository.findAll()
                .stream()
                .filter(person -> person.getId()==Long.parseLong(id, 10))
                .findFirst()
                .orElse(null);
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
     * Получить последнего добавленного пользователя
     * Нужно чтобы при создании связи между Ролью и
     * Пользователем узнать реальный id пользователя
     *
     * @return пользователя
     */
    public Person getLastPerson() {
        return readAll().get(readAll().size() - 1);
    }

    /**
     * Проверка на наличие переанного пользователя
     *
     * @param person переданный пользователь
     * @return если есть, то передает его, иначе null
     */
    public boolean hasPerson(Person person) {
        Person pers = readAll().stream()
                .filter(p -> p.getUsername().equals(person.getUsername()))
                .findFirst().orElse(null);
        return pers != null;
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
