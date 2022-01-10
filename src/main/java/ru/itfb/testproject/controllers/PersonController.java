package ru.itfb.testproject.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.itfb.testproject.exceptions.RoleNotFound;
import ru.itfb.testproject.model.*;
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
@Slf4j
@RestController
@RequestMapping("users")
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
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
        log.info("Using getPersons");
        List<Map<String, String>> AllPersons = new ArrayList<>();
        personService.readAll().forEach(person -> {
            Map<String, String> personMap = person.toMap();
            try {
                personMap.put("role:", getRole(person).toString());
            } catch (RoleNotFound e) {
                System.err.print(e);
            }
            AllPersons.add(personMap);
        });
        return AllPersons;
    }

    /**
     * GET запрос для получения роли пользователя
     *
     * @param person пользователь, роль которого мы хотим узнать
     * @return роль person
     * @throws RoleNotFound ошибка, если не нашли такого пользователя
     */
    public Role getRole(Person person) throws RoleNotFound {
        log.info("Using getRole for" + person);
        if(person == null)
            return null;
        return roleService.read(personRoleService.getIdRoleByIdPerson(person.getId()));
    }

    /**
     * GET запрос для одного пользователя
     *
     * @param id уникальный идентификатор пользователя
     * @return пользователя или ничего, если пользователя с таким id нет
     */
    public Person getOne(String id) {
        log.info("Using getOne for id = " + id);
        return personService.readAll().stream()
                .filter(person -> person.getId() == Long.parseLong(id, 10))
                .findFirst()
                .orElse(null);
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
        log.info("Using getPerson for id = " + id);
        try {
            return new PersonRoleDTO(getOne(id), getRole(getOne(id)));
        } catch (RoleNotFound e) {
            System.err.print(e);
            return new PersonRoleDTO(null, null);
        }
    }

    /**
     * Получить последнего добавленного пользователя
     * Нужно чтобы при создании связи между Ролью и
     * Пользователем узнать реальный id пользователя
     * @return пользователя
     */
    private Person getLastPerson() {
        return personService.readAll().get(personService.readAll().size() - 1);
    }

    /**
     * Получить id переданной роли
     * @param role переданная роль
     * @return id role
     */
    private Long getRoleId(Role role) {
        Role it = roleService.readAll().stream()
                .filter(r -> r.equals(role))
                .findFirst().orElse(null);
        return it != null ? it.getId() : roleService.readAll().size();
    }

    /**
     * Проверка на наличие переданной роли
     * @param role переданная роль
     * @return если есть, то передает ее, иначе null
     */
    private Role hasRole(Role role) {
        return roleService.readAll().stream()
                .filter(r -> r.equals(role))
                .findFirst().orElse(null);
    }

    /**
     * Проверка на наличие переанного пользователя
     * @param person переданный пользователь
     * @return если есть, то передает его, иначе null
     */
    private boolean hasPerson(Person person) {
        Person pers = personService.readAll().stream()
                .filter(p -> p.getUsername().equals(person.getUsername()))
                .findFirst().orElse(null);
        return pers != null;
    }

    /**
     * POST запрос для создания пользователя
     * (Обязательно указывать роль)
     *
     * Если роль уже есть, то она к нему прикрепится.
     * Если нет, то создается новая роль и пользователь
     * @param personRoleDTO пользователь+роль
     * @return пользователь+роль
     */
    @PostMapping
    public Map<String, String> create(@RequestBody PersonRoleDTO personRoleDTO) {
        log.info("Using create for " + personRoleDTO);
        if (!hasPerson(personRoleDTO.getPerson())) {
            personService.save(personRoleDTO.getPerson());
            if (hasRole(personRoleDTO.getRole()) == null)
                roleService.save(personRoleDTO.getRole());
            PersonRole p = new PersonRole(5L, getLastPerson().getId(), getRoleId(personRoleDTO.getRole()));
            personRoleService.save(p);
        }
        return personRoleDTO.getPerson().toMap();//TODO что возвращать?
    }

    /**
     * Обновление пользователя по id
     * Проверяет, есть ли вообще у нас такой пользователь
     * @param id уникальный идентификатор пользователя
     * @param personRoleDTO пользователь+роль
     * @return пользователь+роль
     * @exception RoleNotFound если не найдена роль (такого вроде н может быть)
     */
    @PutMapping("{id}")
    public Map<String, String> update(@PathVariable String id, @RequestBody PersonRoleDTO personRoleDTO) {
        log.info("Using update for id = " + id +" to " + personRoleDTO);
        personService.update(personRoleDTO.getPerson(), Long.parseLong(id, 10));
        Long newId = personRoleService.getIdRoleByIdPerson(Long.parseLong(id, 10));
        try {
            if (!roleService.read(newId).getRole().equals(personRoleDTO.getRole().getRole())) {
                personRoleService.update(new PersonRole(1L, Long.parseLong(id, 10), getRoleId(personRoleDTO.getRole())), personRoleService.getByIdPerson(Long.parseLong(id, 10)).getId());
            }
        } catch (RoleNotFound e) {
            System.err.print(e);
        }
        return personRoleDTO.getPerson().toMap();
    }

    /**
     * DELETE запрос
     * Удаляет пользователя и связь между ним и ролью
     * @param id уникальный идентификатор пользователя
     */
    @DeleteMapping("{id}")
    public void delete(@PathVariable String id) {
        log.info("Using delete for id = " + id);
        personRoleService.delete(personRoleService.getByIdPerson(Long.parseLong(id, 10)).getId());
        personService.delete(Long.parseLong(id, 10));
    }
}
