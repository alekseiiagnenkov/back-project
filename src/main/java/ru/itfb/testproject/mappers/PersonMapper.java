package ru.itfb.testproject.mappers;

import ru.itfb.testproject.entity.Person;
import ru.itfb.testproject.entity.dto.PersonRoleDTO;

public class PersonMapper {
    public static Person dtoToEntity(PersonRoleDTO personRoleDTO){
        return new Person()
                .setUsername(personRoleDTO.getUsername())
                .setPassword(personRoleDTO.getPassword());
    }
}
