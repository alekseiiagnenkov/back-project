package ru.itfb.testproject.entity.dto;

import lombok.*;

/**
 * Класс для одновременной передачи пользователя и роли
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PersonRoleDTO {

    //Person
    private String username;

    private String password;

    //Role
    private String role;

}
