package ru.itfb.testproject.model;

import lombok.*;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "authors")
public class Author {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    public Map<String, String> toMap(){
        String strId = this.id.toString();
        String strFirstName = this.firstName;
        String strLastName = this.lastName;
        return new HashMap<>() {{
            put("id", strId);
            put("first_name", strFirstName);
            put("second_name", strLastName);
        }};
    }
}
