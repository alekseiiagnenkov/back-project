package ru.itfb.testproject.entity;

import lombok.*;

import javax.persistence.*;

/**
 * Класс связи пользователя и его роли
 */
@Getter
@NoArgsConstructor
@Entity
@Table(name = "person_role")
public class PersonRole {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_person")
    private Long idPersons;

    @Column(name = "id_role")
    private Long idRole;

    public PersonRole setId(Long id) {
        this.id = id;
        return this;
    }

    public PersonRole setIdPersons(Long idPersons) {
        this.idPersons = idPersons;
        return this;
    }

    public PersonRole setIdRole(Long idRole) {
        this.idRole = idRole;
        return this;
    }
}