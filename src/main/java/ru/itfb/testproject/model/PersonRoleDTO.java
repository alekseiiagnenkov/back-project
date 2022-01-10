package ru.itfb.testproject.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Класс для одновременной передачи пользователя и роли
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonRoleDTO {

    private Person person;

    private Role role;

}
