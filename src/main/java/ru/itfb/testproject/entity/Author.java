package ru.itfb.testproject.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "authors")
@Getter
@Setter
@ToString
public class Author {

    @Id
    @GeneratedValue
    private Long pk;
    private long id;
    private String firstName;
    private String lastName;

    public Author() {
    }
}
