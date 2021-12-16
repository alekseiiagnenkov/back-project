package ru.itfb.testproject.entity;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "books")
@Getter
@Setter
@ToString
public class Book {

    @Id
    @GeneratedValue
    private Long pk;
    private long id;
    private String name;
    private String author;

    public Book() {
    }
}
