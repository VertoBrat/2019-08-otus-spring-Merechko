drop table if exists BOOKS_AUTHORS;
drop table if exists GENRES;
drop table if exists books;
drop table if exists authors;

create table AUTHORS
(
    ID BIGINT auto_increment,
    FIRST_NAME VARCHAR(255) not null,
    LAST_NAME  VARCHAR(255) not null,
    constraint AUTHORS_PK
        primary key (ID)
);
create table GENRES (
                        id bigint auto_increment,
                        book_id long not null,
                        name varchar(100) not null,
                        constraint genre_pk
                            primary key (id)
);
create unique index genre_name_uindex
    on GENRES (name);

create table BOOKS
(
    ID        BIGINT auto_increment,
    TITLE     VARCHAR(255) not null,
    constraint BOOKS_PK
        primary key (ID)
);

create table BOOKS_AUTHORS
(
    BOOK_ID long not null,
    AUTHOR_ID long not null,
    constraint books_authors_AUTHORS_ID_fk
        foreign key (author_id) references AUTHORS,
    constraint books_authors_BOOKS_ID_fk
        foreign key (book_id) references BOOKS
);




insert into AUTHORS (FIRST_NAME, LAST_NAME) values
('author_1_first_name', 'author_1_last_name'),('author_2_first_name', 'author_2_last_name'),
('author_3_first_name', 'author_3_last_name');

insert into BOOKS (TITLE) values
('title_1'), ('title_2'),
('title_3');

insert into GENRES (book_id, NAME)values
(1, 'genre_1'), (2, 'genre_2'), (3, 'genre_3');

insert into books_authors (book_id, author_id)
values (1, 1),(1, 2), (1, 3), (2, 2), (3, 3);
