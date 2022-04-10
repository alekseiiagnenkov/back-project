package ru.itfb.backproject.service;

import org.springframework.stereotype.Service;
import ru.itfb.backproject.entity.Person;
import ru.itfb.backproject.entity.PersonRole;
import ru.itfb.backproject.entity.Role;
import ru.itfb.backproject.repositories.PersonRepository;

import java.util.List;

/**
 * Service для {@link Person}
 * Тут прописывал основные функции, в которых нужна была связь с БД
 */
@Service
public class PersonService {

    private final RoleService roleService;
    private final PersonRoleService personRoleService;
    private final PersonRepository personRepository;

    public PersonService(RoleService roleService, PersonRoleService personRoleService, PersonRepository personRepository) {
        this.roleService = roleService;
        this.personRoleService = personRoleService;
        this.personRepository = personRepository;
    }

    public Person findPersonByUsername(String username) {
        return personRepository.findUserByUsername(username);
    }

    /**
     * Сохранение пользователя в таблицу
     *
     * @param person пользователь, которого нужно сохранить
     */
    public boolean save(Person person, Role role) {
        if (!hasPerson(person)) {
            personRepository.save(person);
            if (roleService.hasRole(role) == null)
                roleService.save(role);
            personRoleService.save(
                    new PersonRole()
                            .setId(-1L)
                            .setIdPersons(getLastPerson().getId())
                            .setIdRole(roleService.getRoleId(role))
            );
            return true;
        }
        return false;
    }

    /**
     * Считывание всех пользователей из БД
     *
     * @return лист пользователей
     */
    public List<Person> readAll() {
        return personRepository.findAll();
    }

    /**
     * Считывание пользователя из БД по id
     *
     * @param id уникальный идентификатор пользователя
     * @return пользователя
     */
    public Person getOne(String id) {
        return personRepository.findAll()
                .stream()
                .filter(person -> person.getId() == Long.parseLong(id, 10))
                .findFirst()
                .orElse(null);
    }

    /**
     * Для получения роли пользователя
     *
     * @param person пользователь, роль которого мы хотим узнать
     * @return роль person
     */
    public Role getRole(Person person) {
        if (person == null)
            return null;
        return roleService.read(personRoleService.getIdRoleByIdPerson(person.getId()));
    }

    /**
     * Обновление пользователя
     *
     * @param person новые данные пользователя
     * @param id            уникальный идентификатор, по которому будут заменены данные
     * @return true, если обновилось, false если не нашел такого
     */
    public boolean update(Person person, Role role, Long id) {
        Long idRole = personRoleService.getIdRoleByIdPerson(id);
        if (!roleService.read(idRole).getRole().equals(role.getRole())) {
            personRoleService.update(new PersonRole()
                    .setId(-1L)
                    .setIdPersons(id)
                    .setIdRole(roleService.getRoleId(role)), personRoleService.getByIdPerson(id).getId());
        }

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
     *
     * @param id уникальный идентификатор пользователя
     * @return true, если удалилось, false если не нашел такого
     */
    public boolean delete(Long id) {
        personRoleService.delete(personRoleService.getByIdPerson(id).getId());
        if (personRepository.existsById(id)) {
            personRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
