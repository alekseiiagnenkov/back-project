package ru.itfb.testproject.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ru.itfb.testproject.other.PersonDetails;
import ru.itfb.testproject.entity.Person;

/**
 * Собственный UserDetailsService, чтобы научить security считывать из моей БД
 */
public class UserDetailsServiceImpl implements UserDetailsService {

    private final PersonService personService;
    private final PersonRoleService personRoleService;
    private final RoleService roleService;

    public UserDetailsServiceImpl(PersonService personService, PersonRoleService personRoleService, RoleService roleService) {
        this.personService = personService;
        this.personRoleService = personRoleService;
        this.roleService = roleService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Person person = personService.findPersonByUsername(username);
        return new PersonDetails(person,"ROLE_"+roleService.read(personRoleService.getIdRoleByIdPerson(person.getId())).getRole());
    }

}
