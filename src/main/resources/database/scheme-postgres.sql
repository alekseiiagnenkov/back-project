/*CREATE USER admin WITH password 'admin';

CREATE DATABASE library OWNER admin;

DROP TABLE books, author_book, authors, persons;*/

CREATE TABLE authors
(
    id         BIGINT NOT NULL,
    first_name VARCHAR(100),
    last_name  VARCHAR(100)
);

CREATE TABLE books
(
    id   BIGINT NOT NULL,
    name VARCHAR(255)
);

CREATE TABLE author_book
(
    id        BIGINT NOT NULL,
    id_author BIGINT NOT NULL,
    id_book   BIGINT NOT NULL
);

CREATE TABLE persons
(
    id       BIGINT NOT NULL,
    username VARCHAR(100),
    password VARCHAR(100)
);
