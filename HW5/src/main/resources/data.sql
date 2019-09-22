insert into AUTHORS (ID, FIRST_NAME, LAST_NAME) values
     (1, 'NoFirstName', 'NoLastName'),(2, 'Robert', 'Shekli'), (3, 'Joshua', 'Bloh'),(4, 'Nikolay', 'Gogol');

insert into GENRES(ID, NAME) values
(1, 'Story'), (2, 'Novel'), (3, 'Comedy');

insert into BOOKS (ID,GENRE_ID, TITLE) values
(1, 3, 'Missing Certificate'), (2, 1, 'Civilization of status'),
(3, 2, 'Effective_Java_3rd');

insert into BOOKS_AUTHORS (BOOK_ID, AUTHOR_ID)
values (1, 4),(1, 1), (2, 2), (3, 3);
