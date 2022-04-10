package ru.itfb.backproject.entity.dto;

import lombok.*;

/**
 * Класс для одновременной передачи пользователя и роли
 */
@Getter
@NoArgsConstructor
public class PersonRoleDTO {

    //Person
    private String username;

    private String password;

    //Role
    private String role;

    public PersonRoleDTO setUsername(String username) {
        this.username = username;
        return this;
    }

    public PersonRoleDTO setPassword(String password) {
        this.password = password;
        return this;
    }

    public PersonRoleDTO setRole(String role) {
        this.role = role;
        return this;
    }

    @Override
    public String toString() {
        return "{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
