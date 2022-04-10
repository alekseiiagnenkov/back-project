package ru.itfb.backproject.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Класс пользователя
 */
@Getter
@NoArgsConstructor
@Entity
@Table(name = "persons")
public class Person {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String username;

    @Column
    private String password;

    public Person setId(Long id) {
        this.id = id;
        return this;
    }

    public Person setUsername(String username) {
        this.username = username;
        return this;
    }

    public Person setPassword(String password) {
        this.password = password;
        return this;
    }

    /**
     * Для преобразования в map, так как иногда нужно было добавлять сюда еще и role
     * @return map со всеми полями
     */
    public Map<String, String> toMap(){
        String strId = this.id.toString();
        String strFirstName = this.username;
        String strLastName = this.password;
        return new HashMap<>() {{
            put("id", strId);
            put("username", strFirstName);
            put("password", strLastName);
        }};
    }
}
