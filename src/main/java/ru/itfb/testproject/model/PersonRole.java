package ru.itfb.testproject.model;

import lombok.*;

import javax.persistence.*;

/**
 * Класс связи пользователя и его роли
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "person_role")
public class PersonRole {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long id_person;

    @Column
    private Long id_role;
}