package ru.itfb.testproject.service;

import org.springframework.stereotype.Service;
import ru.itfb.testproject.exceptions.RoleNotFound;
import ru.itfb.testproject.entity.Role;
import ru.itfb.testproject.repositories.RoleRepository;

import java.util.List;

/**
 * Service для {@link Role}
 * Тут прописывал основные функции, в которых нужна была связь с БД
 */
@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    /**
     * Сохранение роли в таблицу
     * @param role роль, которую нужно сохранить
     */
    public void save(Role role) {
        roleRepository.save(role);
    }

    public List<Role> readAll() {
        return roleRepository.findAll();
    }

    /**
     * Считывание всех ролей из БД
     * @return лист ролей
     */
    public Role read(Long id) throws RoleNotFound {
        return roleRepository.findById(id).orElseThrow(RoleNotFound::new);
    }

    /**
     * Обновление роли
     * @param role новые данные роли
     * @param id уникальный идентификатор, по которому будут заменены данные
     * @return true, если обновилось, false если не нашел такого
     */
    public boolean update(Role role, Long id) {
        if (roleRepository.existsById(id)) {
            role.setId(id);
            roleRepository.save(role);
            return true;
        }
        return false;
    }

    /**
     * Удаление роли из БД
     * @param id уникальный идентификатор роли
     * @return true, если удалилось, false если не нашел такого
     */
    public boolean delete(Long id) {
        if (roleRepository.existsById(id)) {
            roleRepository.deleteById(id);
            return true;
        }
        return false;
    }
}