package ru.itfb.backproject.mappers;

import org.springframework.stereotype.Component;
import ru.itfb.backproject.entity.Person;
import ru.itfb.backproject.entity.Role;
import ru.itfb.backproject.entity.dto.PersonRoleDTO;
import ru.itfb.backproject.service.PersonService;

@Component
public class PersonMapper {

    public Role dtoToRole(PersonRoleDTO personRoleDTO){
        return new Role()
                .setRole(personRoleDTO.getRole());
    }

    public Person dtoToPerson(PersonRoleDTO personRoleDTO) {
        return new Person()
                .setUsername(personRoleDTO.getUsername())
                .setPassword(personRoleDTO.getPassword());
    }

    public PersonRoleDTO PersonToDto(Person person, PersonService personService) {
        return new PersonRoleDTO()
                .setUsername(person.getUsername())
                .setPassword(person.getPassword())
                .setRole(personService.getRole(person).toString());
    }
}
