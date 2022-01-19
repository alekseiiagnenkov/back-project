package ru.itfb.testproject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.itfb.testproject.entity.Person;
import ru.itfb.testproject.entity.Role;
import ru.itfb.testproject.entity.dto.PersonRoleDTO;
import ru.itfb.testproject.mappers.PersonMapper;
import ru.itfb.testproject.service.PersonService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Контроллер для {@link Person}(для всех авторизированных пользователей)
 * По сути готовый backend(если я правильно все понял)
 * <p>
 * --------ДОСТУПЕН ТОЛЬКО для пользователя admin--------
 */
@RestController
public class PersonController {

    private final PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    /**
     * Дает информацию об авторизированном пользователе
     *
     * @param request чтобы получить информацию о пользователе
     * @return страницу about.html
     */
    @RequestMapping(value = "about", method = RequestMethod.GET)
    public String about(HttpServletRequest request, PersonMapper personMapper) {
        Person person = personService.findPersonByUsername(request.getRemoteUser());

        if (person != null) {
            return personMapper.PersonToDto(person, personService).toString();
        } else {
            return "Not authorized";
        }
    }

    /**
     * GET запрос всех пользователей
     *
     * @return массив всех пользователей вместе с их ролями
     */
    @RequestMapping(value = "users", method = RequestMethod.GET)
    public List<PersonRoleDTO> getPersons(PersonMapper personMapper) {
        return personService.readAll()
                .stream()
                .map(person -> personMapper.PersonToDto(person, personService))
                .toList();
    }

    /**
     * GET запрос для получения пользователя по id
     * Будет пусто, если такой пользователь не найден
     *
     * @param id уникальный идентификатор пользователя
     * @return пользователя с ролью или пустоту
     */
    @RequestMapping(value = "users/{id}", method = RequestMethod.GET)
    public PersonRoleDTO getPerson(@PathVariable String id, PersonMapper personMapper) {
        return personMapper.PersonToDto(personService.getOne(id), personService);
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
    @RequestMapping(value = "users", method = RequestMethod.POST)
    public boolean create(@RequestBody PersonRoleDTO personRoleDTO, PersonMapper personMapper) {
        Person person = personMapper.dtoToPerson(personRoleDTO);
        Role role = personMapper.dtoToRole(personRoleDTO);
        return personService.save(person, role);

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
    public boolean update(@PathVariable String id, @RequestBody PersonRoleDTO personRoleDTO, PersonMapper personMapper) {
        Person person = personMapper.dtoToPerson(personRoleDTO);
        Role role = personMapper.dtoToRole(personRoleDTO);
        return personService.update(person, role, Long.parseLong(id, 10));
    }

    /**
     * DELETE запрос
     * Удаляет пользователя и связь между ним и ролью
     *
     * @param id уникальный идентификатор пользователя
     */
    @DeleteMapping("{id}")
    public void delete(@PathVariable String id) {
        personService.delete(Long.parseLong(id, 10));
    }
}
