GRANT SELECT ON persons, authors, books, author_book, roles, person_role TO PUBLIC;

DROP DATABASE postgres;


/*authors*/
DROP TABLE authors;

CREATE TABLE authors
(
    id       SERIAL,
    first_name VARCHAR(100),
    last_name  VARCHAR(100)
);

/*books*/
DROP TABLE books;

CREATE TABLE books
(
    id       SERIAL,
    name VARCHAR(255)
);

/*author_book*/
DROP TABLE author_book;

CREATE TABLE author_book
(
    id       SERIAL,
    id_author BIGINT NOT NULL,
    id_book   BIGINT NOT NULL
);


/*persons*/
DROP TABLE persons;

CREATE TABLE persons
(
    id       SERIAL,
    username VARCHAR(100),
    password VARCHAR(100)
);

/*roles*/
DROP TABLE roles;

CREATE TABLE roles
(
    id       SERIAL,
    role VARCHAR(20)
);

/*person_role*/
DROP TABLE person_role;

CREATE TABLE person_role
(
    id       SERIAL,
    id_person BIGINT NOT NULL,
    id_role   BIGINT NOT NULL
);
