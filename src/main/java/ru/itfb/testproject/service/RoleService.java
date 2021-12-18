package ru.itfb.testproject.service;

import org.springframework.stereotype.Service;
import ru.itfb.testproject.model.Role;
import ru.itfb.testproject.repositories.RoleRepository;

import java.util.List;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public void create(Role role) {
        roleRepository.save(role);
    }

    public List<Role> readAll() {
        return roleRepository.findAll();
    }

    public Role read(Long id) {
        return roleRepository.findById(id).get();
    }

    public boolean update(Role role, Long id) {
        if (roleRepository.existsById(id)) {
            role.setId(id);
            roleRepository.save(role);
            return true;
        }

        return false;
    }

    public boolean delete(Long id) {
        if (roleRepository.existsById(id)) {
            roleRepository.deleteById(id);
            return true;
        }
        return false;
    }
}