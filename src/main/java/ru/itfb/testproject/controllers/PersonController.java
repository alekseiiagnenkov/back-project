package ru.itfb.testproject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.itfb.testproject.entity.Person;
import ru.itfb.testproject.entity.PersonRole;
import ru.itfb.testproject.entity.Role;
import ru.itfb.testproject.entity.dto.PersonRoleDTO;
import ru.itfb.testproject.exceptions.RoleNotFound;
import ru.itfb.testproject.mappers.PersonMapper;
import ru.itfb.testproject.mappers.RoleMapper;
import ru.itfb.testproject.service.PersonRoleService;
import ru.itfb.testproject.service.PersonService;
import ru.itfb.testproject.service.RoleService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Контроллер для {@link Person}(для всех авторизированных пользователей)
 * По сути готовый backend(если я правильно все понял)
 * <p>
 * --------ДОСТУПЕН ТОЛЬКО для пользователя admin--------
 */
@RestController
@RequestMapping("users")
public class PersonController {

    private final PersonService personService;
    private final RoleService roleService;
    private final PersonRoleService personRoleService;

    @Autowired
    public PersonController(PersonService personService, RoleService roleService, PersonRoleService personRoleService) {
        this.personService = personService;
        this.roleService = roleService;
        this.personRoleService = personRoleService;
    }

    /**
     * GET запрос всех пользователей
     *
     * @return массив всех пользователей вместе с их ролями
     */
    @GetMapping
    public List<Map<String, String>> getPersons() {
        List<Map<String, String>> AllPersons = new ArrayList<>();
        personService.readAll().forEach(person -> {
            Map<String, String> personMap = person.toMap();
            try {
                personMap.put("role:", getRole(person).toString());
            } catch (RoleNotFound e) {
                e.printStackTrace();
            }
            AllPersons.add(personMap);
        });
        return AllPersons;
    }

    /**
     * Для получения роли пользователя
     *
     * @param person пользователь, роль которого мы хотим узнать
     * @return роль person
     * @throws RoleNotFound ошибка, если не нашли такого пользователя
     */
    public Role getRole(Person person) throws RoleNotFound {
        if (person == null)
            return null;
        return roleService.read(personRoleService.getIdRoleByIdPerson(person.getId()));
    }

    /**
     * GET запрос для получения пользователя по id
     * Будет пусто, если такой пользователь не найден
     *
     * @param id уникальный идентификатор пользователя
     * @return пользователя с ролью или пустоту
     */
    @GetMapping("{id}")
    public PersonRoleDTO getPerson(@PathVariable String id) {
        try {
            Person person = personService.getOne(id);
            return new PersonRoleDTO(person.getUsername(), person.getPassword(), getRole(personService.getOne(id)).getRole());
        } catch (RoleNotFound e) {
            return new PersonRoleDTO("", "", "");
        }
    }

    /**
     * POST запрос для создания пользователя
     * (Обязательно указывать роль)
     * <p>
     * Если роль уже есть, то она к нему прикрепится.
     * Если нет, то создается новая роль и пользователь
     *
     * @param personRoleDTO пользователь+роль
     * @return пользователь+роль
     */
    @PostMapping
    public Person create(@RequestBody PersonRoleDTO personRoleDTO) {
        Person person = PersonMapper.dtoToEntity(personRoleDTO);
        Role role = RoleMapper.dtoToEntity(personRoleDTO);
        if (!personService.hasPerson(person)) {
            personService.save(person);
            if (roleService.hasRole(role) == null)
                roleService.save(role);
            PersonRole p = new PersonRole()
                    .setId(-1L)
                    .setIdPersons(personService.getLastPerson().getId())
                    .setIdRole(roleService.getRoleId(role));
            personRoleService.save(p);
            return personService.getLastPerson();
        }
        return null;
    }

    /**
     * Обновление пользователя по id
     * Проверяет, есть ли вообще у нас такой пользователь
     *
     * @param id            уникальный идентификатор пользователя
     * @param personRoleDTO пользователь+роль
     * @return пользователь+роль
     */
    @PutMapping("{id}")
    public Person update(@PathVariable String id, @RequestBody PersonRoleDTO personRoleDTO) {
        Person person = PersonMapper.dtoToEntity(personRoleDTO);
        Role role = RoleMapper.dtoToEntity(personRoleDTO);
        personService.update(person, Long.parseLong(id, 10));
        Long newId = personRoleService.getIdRoleByIdPerson(Long.parseLong(id, 10));
        if (!roleService.read(newId).getRole().equals(role.getRole())) {
            personRoleService.update(new PersonRole().setId(-1L).setIdPersons(Long.parseLong(id, 10)).setIdRole(roleService.getRoleId(role)), personRoleService.getByIdPerson(Long.parseLong(id, 10)).getId());
        }
        return personService.getOne(id);
    }

    /**
     * DELETE запрос
     * Удаляет пользователя и связь между ним и ролью
     *
     * @param id уникальный идентификатор пользователя
     */
    @DeleteMapping("{id}")
    public void delete(@PathVariable String id) {
        personRoleService.delete(personRoleService.getByIdPerson(Long.parseLong(id, 10)).getId());
        personService.delete(Long.parseLong(id, 10));
    }
}
