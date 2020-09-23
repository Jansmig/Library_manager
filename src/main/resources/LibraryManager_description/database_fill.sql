INSERT INTO ORIGINS(id, author, isbn, published_year, title)
VALUES(1, "J. R. R. Tolkien", "0345339681", 1937, "The Hobbit");

INSERT INTO ORIGINS(id, author, isbn, published_year, title)
VALUES(2, "Antoine de Saint-Exupery", "0756751896", 1943, "The Little Prince");

INSERT INTO ORIGINS(id, author, isbn, published_year, title)
VALUES(3, "Agatha Christie", "9780062073488", 1939, "And Then There Were None");

INSERT INTO ORIGINS(id, author, isbn, published_year, title)
VALUES(4, "Dan Brown", "0385504209", 2003, "The Da Vinci Code");

INSERT INTO BOOKS (id, book_status, origin_id)
VALUES(5, 0, 1);

INSERT INTO BOOKS (id, book_status, origin_id)
VALUES(6, 1, 1);

INSERT INTO BOOKS (id, book_status, origin_id)
VALUES(7, 2, 2);

INSERT INTO BOOKS (id, book_status, origin_id)
VALUES(8, 0, 3);

INSERT INTO BOOKS (id, book_status, origin_id)
VALUES(9, 0, 3);

INSERT INTO BOOKS (id, book_status, origin_id)
VALUES(10, 1, 4);

# korzystając z postmana user creation_date uzupełnia się automatycznie

INSERT INTO USERS (id, email, first_name, last_name, user_creation_date)
VALUES(11, "bobs@wp.pl", "Bob", "Smith", null);

INSERT INTO USERS (id, email, first_name, last_name, user_creation_date)
VALUES(12, "andys@gmail.com", "Andy", "White", null);

INSERT INTO USERS (id, email, first_name, last_name, user_creation_date)
VALUES(13, "sarahs@gmail.com", "Sarah", "Connor", null);

# korzystając z postmana (wypożyczenie / zwrot) daty uzupełniają się automatycznie

INSERT INTO RENTALS (id, active, rental_date, return_date, book_id, user_id)
VALUES(14, 1, null, null, 6, 11);

INSERT INTO RENTALS (id, active, rental_date, return_date, book_id, user_id)
VALUES(15, 1, null, null, 10, 13);

commit;



