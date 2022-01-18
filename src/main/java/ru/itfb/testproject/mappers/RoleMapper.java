package ru.itfb.testproject.mappers;

import ru.itfb.testproject.entity.Role;
import ru.itfb.testproject.entity.dto.PersonRoleDTO;

public class RoleMapper {
    public static Role dtoToEntity(PersonRoleDTO personRoleDTO){
        return new Role()
                .setRole(personRoleDTO.getRole());
    }
}
