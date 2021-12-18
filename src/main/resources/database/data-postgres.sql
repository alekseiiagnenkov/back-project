INSERT INTO authors (id, first_name, last_name)
VALUES (1, 'Lev', 'Tolstoy'),
       (2, 'Ilya', 'Ilf'),
       (3, 'Evgeny', 'Petrov');

INSERT INTO books (id, name)
VALUES (1, '12 chairs'),
       (2, 'War and Peace');

INSERT INTO author_book (id, id_author, id_book)
VALUES (1, 1, 3),
       (2, 2, 1);

INSERT INTO persons (id, username, password)
VALUES (1, 'admin', 'admin'),
       (2, 'user', 'user');

INSERT INTO roles (id, role)
VALUES (1, 'ADMIN'),
       (2, 'USER');

INSERT INTO person_role (id, id_person, id_role)
VALUES (1, 1, 1),
       (2, 2, 2);