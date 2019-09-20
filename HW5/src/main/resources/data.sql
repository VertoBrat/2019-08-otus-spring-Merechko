insert into AUTHORS (ID, FIRST_NAME, LAST_NAME) values
     (1, 'NoFirstName', 'NoLastName'),(2, 'Robert', 'Shekli'), (3, 'Joshua', 'Bloh'),(4, 'Nikolay', 'Gogol');
insert into genre(id, name) values
(1, 'Story'), (2, 'Novel'), (3, 'Comedy');
insert into BOOKS (ID, TITLE, GENRE_ID) values
     (1, 'Missing Certificate', 3), (2, 'Civilization of status', 1),
     (3, 'Effective_Java_3rd', 2);

insert into books_authors (book_id, author_id)
values (1, 4), (2, 2), (3, 3);
