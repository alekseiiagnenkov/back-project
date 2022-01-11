package ru.itfb.testproject.entity;

import lombok.*;

import javax.persistence.*;

/**
 * Класс автора книги
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "authors")
public class Author {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    /**
     * Реализация сравнения
     * @param author другой автор
     * @return true, если совпадает имя и фамилия, иначе false
     */
    public boolean equals(Author author){
        if(this == author)
            return true;
        return author.firstName.equals(this.firstName) && author.lastName.equals(this.lastName);
    }
}
