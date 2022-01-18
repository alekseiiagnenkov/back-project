package ru.itfb.testproject.entity;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

/**
 * Класс роли
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "roles")
public class Role implements GrantedAuthority {

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

    @Override
    public String getAuthority() {
        return role;
    }
}
