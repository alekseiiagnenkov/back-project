package ru.itfb.testproject.entity.dto;

import lombok.*;
import ru.itfb.testproject.entity.Person;
import ru.itfb.testproject.entity.Role;

/**
 * Класс для одновременной передачи пользователя и роли
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PersonRoleDTO {

    private Person person;

    private Role role;

}
