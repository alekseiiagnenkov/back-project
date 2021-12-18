package ru.itfb.testproject.model;

import lombok.*;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "books")
public class Book {

    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Column
    private String name;

    public Map<String, String> toMap(){
        String strId = this.id.toString();
        String strName = this.name;
        return new HashMap<>() {{
            put("id", strId);
            put("name", strName);
        }};
    }
}
