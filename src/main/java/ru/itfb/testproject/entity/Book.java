package ru.itfb.testproject.entity;

import lombok.*;

import javax.persistence.*;

/**
 * Класс книги
 */
@Getter
@NoArgsConstructor
@Entity
@Table(name = "books")
public class Book {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    public Book setId(Long id) {
        this.id = id;
        return this;
    }

    public Book setName(String name) {
        this.name = name;
        return this;
    }
}
