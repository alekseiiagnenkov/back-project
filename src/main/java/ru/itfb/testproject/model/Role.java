package ru.itfb.testproject.model;

import lombok.*;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "roles")
public class Role {

    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "role")
    private String role;

    public Map<String, String> toMap(){
        String strId = this.id.toString();
        String strRole = this.role;
        return new HashMap<>() {{
            put("id", strId);
            put("role", strRole);
        }};
    }

    public String toString() {
        return role;
    }
}
