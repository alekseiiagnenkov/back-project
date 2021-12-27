package ru.itfb.testproject.service;

import org.springframework.stereotype.Service;
import ru.itfb.testproject.model.PersonRole;
import ru.itfb.testproject.repositories.PersonRoleRepository;

import java.util.List;

@Service
public class PersonRoleService {

    private final PersonRoleRepository personRoleRepository;

    public PersonRoleService(PersonRoleRepository personRoleRepository) {
        this.personRoleRepository = personRoleRepository;
    }

    public void create(PersonRole personRole) {
        personRoleRepository.save(personRole);
    }

    public List<PersonRole> readAll() {
        return personRoleRepository.findAll();
    }


    public boolean update(PersonRole personRole, Long id) {
        if (personRoleRepository.existsById(id)) {
            personRole.setId(id);
            personRoleRepository.save(personRole);
            return true;
        }

        return false;
    }

    public PersonRole getByIDPerson(Long id_person) {
        return personRoleRepository.findAll().stream()
                .filter(id_p -> id_p.getId_person().equals(id_person))
                .findFirst().orElse(null);
    }

    public Long getIdRoleByIdPerson(Long id_person) {
        return getByIDPerson(id_person).getId_role();
    }

    public boolean delete(Long id) {
        if (personRoleRepository.existsById(id)) {
            personRoleRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
