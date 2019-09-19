insert into AUTHORS (ID, FIRST_NAME, LAST_NAME) values
     (1, 'Robert', 'Shekli'), (2, 'Joshua', 'Bloh'),(3, 'Nikolay', 'Gogol');
insert into BOOKS (ID, TITLE) values
     (1, 'Missing Certificate'), (2, 'Civilization of status'),
     (3, 'Effective_Java_3rd');
insert into books_authors (book_id, author_id)
values (1, 3), (2, 1), (3, 2);
insert into BOOKS_GENRES (BOOK_ID, NAME) values
(3, 'Novel'),(2, 'Story'),(1, 'Comedy');
