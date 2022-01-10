package ru.itfb.testproject.service;

import org.springframework.stereotype.Service;
import ru.itfb.testproject.model.PersonRole;
import ru.itfb.testproject.repositories.PersonRoleRepository;

import java.util.List;

/**
 * Service для {@link PersonRole}
 * Тут прописывал основные функции, в которых нужна была связь с БД
 */
@Service
public class PersonRoleService {

    private final PersonRoleRepository personRoleRepository;

    public PersonRoleService(PersonRoleRepository personRoleRepository) {
        this.personRoleRepository = personRoleRepository;
    }

    /**
     * Сохранение связи в таблицу
     * @param personRole связь, которого нужно сохранить
     */
    public void save(PersonRole personRole) {
        personRoleRepository.save(personRole);
    }

    /**
     * Считывание всех связей между пользователями и ролями из БД
     * @return лист связей
     */
    public List<PersonRole> readAll() {
        return personRoleRepository.findAll();
    }

    /**
     * Обновление связи
     * @param personRole новые данные связи
     * @param id уникальный идентификатор, по которому будут заменены данные
     * @return true, если обновилось, false если не нашел такого
     */
    public boolean update(PersonRole personRole, Long id) {
        if (personRoleRepository.existsById(id)) {
            personRole.setId(id);
            personRoleRepository.save(personRole);
            return true;
        }
        return false;
    }

    /**
     * Получить связь по id пользователя
     * @param id_person id пользователя
     * @return связь или null
     */
    public PersonRole getByIdPerson(Long id_person) {
        return personRoleRepository.findAll().stream()
                .filter(id_p -> id_p.getId_person().equals(id_person))
                .findFirst().orElse(null);
    }

    /**
     * Получить id роли по id пользователя
     * @param id_person id пользователя
     * @return id его роли
     */
    public Long getIdRoleByIdPerson(Long id_person) {
        return getByIdPerson(id_person).getId_role();
    }

    /**
     * Удаление связи из БД
     * @param id уникальный идентификатор связи
     * @return true, если удалилось, false если не нашел такого
     */
    public boolean delete(Long id) {
        if (personRoleRepository.existsById(id)) {
            personRoleRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
