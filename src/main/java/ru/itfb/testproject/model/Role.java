package ru.itfb.testproject.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;

/**
 * Класс роли
 */
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

    public String toString() {
        return role;
    }

    /**
     * Сравнение ролей
     * @param role другая роль
     * @return true, если равны, иначе false
     */
    public boolean equals(Role role) {
        if (this == role) {
            return true;
        }
        return role.getRole().equals(this.getRole());
    }
}
