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
VALUES (1, 'admin', 'admin1'),
       (2, 'user', 'user1');