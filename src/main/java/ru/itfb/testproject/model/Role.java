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
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "role")
    private String role;

    public Map<String, String> toMap() {
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

    public boolean equals(Role role) {
        if (this == role) {
            return true;
        }
        return role.getRole().equals(this.getRole());
    }
}
