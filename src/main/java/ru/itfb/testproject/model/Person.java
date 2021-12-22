package ru.itfb.testproject.model;

import lombok.*;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;


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
