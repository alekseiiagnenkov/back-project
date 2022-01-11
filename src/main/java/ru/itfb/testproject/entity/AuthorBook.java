package ru.itfb.testproject.entity;

import lombok.*;

import javax.persistence.*;

/**
 * Класс связь между автором и книгой
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "author_book")
public class AuthorBook {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long id_author;

    @Column
    private Long id_book;
}
