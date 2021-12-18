package ru.itfb.testproject.model;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "author_book")
public class AuthorBook {

    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "id_author", nullable = false)
    private Long idAuthor;

    @Column(name = "id_book", nullable = false)
    private Long idBook;
}
