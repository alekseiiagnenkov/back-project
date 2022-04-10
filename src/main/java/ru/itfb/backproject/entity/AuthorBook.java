package ru.itfb.backproject.entity;

import lombok.*;

import javax.persistence.*;

/**
 * Класс связь между автором и книгой
 */
@Getter
@NoArgsConstructor
@Entity
@Table(name = "author_book")
public class AuthorBook {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_author")
    private Long idAuthor;

    @Column(name = "id_book")
    private Long idBook;

    public AuthorBook setId(Long id) {
        this.id = id;
        return this;
    }

    public AuthorBook setIdAuthor(Long idAuthor) {
        this.idAuthor = idAuthor;
        return this;
    }

    public AuthorBook setIdBook(Long idBook) {
        this.idBook = idBook;
        return this;
    }
}
