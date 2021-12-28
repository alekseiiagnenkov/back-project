package ru.itfb.testproject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.itfb.testproject.model.Person;
import ru.itfb.testproject.model.PersonRole;
import ru.itfb.testproject.model.PersonRoleDTO;
import ru.itfb.testproject.model.Role;
import ru.itfb.testproject.service.PersonRoleService;
import ru.itfb.testproject.service.PersonService;
import ru.itfb.testproject.service.RoleService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    @GetMapping
    public List<Map<String, String>> persons() {
        List<Map<String, String>> AllPersons = new ArrayList<>();
        personService.readAll().forEach(person -> {
            Map<String, String> personMap = person.toMap();
            personMap.put("role:", getRole(person).toString());
            AllPersons.add(personMap);
        });
        return AllPersons;
    }

    public Role getRole(Person person) {
        return roleService.read(personRoleService.getIdRoleByIdPerson(person.getId()));
    }

    public Person getOne(String id) {
        return personService.readAll().stream()
                .filter(person -> person.getId() == Long.parseLong(id, 10))
                .findFirst()
                .orElse(null);
    }

    @GetMapping("{id}")
    public Map<String, String> getPerson(@PathVariable String id) {
        Map<String, String> personMap = getOne(id).toMap();
        personMap.put("role:", getRole(getOne(id)).toString());
        return personMap;
    }

    private Person getLastPerson() {
        return personService.readAll().get(personService.readAll().size() - 1);
    }

    private Long getRoleId(Role role) {
        Role it = roleService.readAll().stream()
                .filter(r -> r.equals(role))
                .findFirst().orElse(null);
        return it != null ? it.getId() : roleService.readAll().size();
    }

    private Role hasRole(Role role) {
        return roleService.readAll().stream()
                .filter(r -> r.equals(role))
                .findFirst().orElse(null);
    }

    private boolean hasPerson(Person person) {
        Person pers = personService.readAll().stream()
                .filter(p -> p.getUsername().equals(person.getUsername()))
                .findFirst().orElse(null);
        return pers != null;
    }

    @PostMapping
    public Map<String, String> create(@RequestBody PersonRoleDTO personRoleDTO) {
        if (!hasPerson(personRoleDTO.getPerson())) {
            personService.create(personRoleDTO.getPerson());
            if (hasRole(personRoleDTO.getRole()) == null)
                roleService.create(personRoleDTO.getRole());
            PersonRole p = new PersonRole(5L, getLastPerson().getId(), getRoleId(personRoleDTO.getRole()));
            personRoleService.create(p);
        }
        return personRoleDTO.getPerson().toMap();//TODO что возвращать?
    }

    @PutMapping("{id}")
    public Map<String, String> update(@PathVariable String id, @RequestBody PersonRoleDTO personRoleDTO) {
        personService.update(personRoleDTO.getPerson(), Long.parseLong(id, 10));
        Long newId = personRoleService.getIdRoleByIdPerson(Long.parseLong(id, 10));
        if (!roleService.read(newId).getRole().equals(personRoleDTO.getRole().getRole())) {
            personRoleService.update(new PersonRole(1L, Long.parseLong(id, 10), getRoleId(personRoleDTO.getRole())), personRoleService.getByIDPerson(Long.parseLong(id, 10)).getId());
        }
        return personRoleDTO.getPerson().toMap();
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable String id) {
        personRoleService.delete(personRoleService.getByIDPerson(Long.parseLong(id, 10)).getId());
        personService.delete(Long.parseLong(id, 10));
    }
}
