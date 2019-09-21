insert into AUTHORS (ID, FIRST_NAME, LAST_NAME) values
     (1, 'NoFirstName', 'NoLastName'),(2, 'Robert', 'Shekli'), (3, 'Joshua', 'Bloh'),(4, 'Nikolay', 'Gogol');

insert into BOOKS (ID, TITLE) values
(1, 'Missing Certificate'), (2, 'Civilization of status'),
(3, 'Effective_Java_3rd');

insert into genres(id, book_id, name) values
(1, 2, 'Story'), (2, 3, 'Novel'), (3, 1, 'Comedy');

insert into books_authors (book_id, author_id)
values (1, 4),(1, 1), (2, 2), (3, 3);
