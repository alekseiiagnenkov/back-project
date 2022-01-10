package ru.itfb.testproject.model;

import lombok.*;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Класс пользователя
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
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
