drop table if exists BOOKS_AUTHORS;
drop table if exists BOOKS;
drop table if exists GENRES;
drop table if exists AUTHORS;

create table AUTHORS
(
    ID         long auto_increment,
    FIRST_NAME VARCHAR(255) not null,
    LAST_NAME  VARCHAR(255) not null,
    constraint AUTHORS_PK
        primary key (ID)
);
create table GENRES
(
    ID   long auto_increment,
    NAME varchar(100) not null,
    constraint genre_pk
        primary key (ID)
);

create unique index genre_name_uindex
    on GENRES (NAME);

create table BOOKS
(
    ID       long auto_increment,
    GENRE_ID long         not null,
    TITLE    VARCHAR(255) not null,
    constraint BOOKS_PK
        primary key (ID),
    constraint books_genres_genre_id_fk
        foreign key (GENRE_ID) references GENRES
);

create table BOOKS_AUTHORS
(
    BOOK_ID   long not null,
    AUTHOR_ID long not null,
    constraint books_authors_AUTHORS_ID_fk
        foreign key (author_id) references AUTHORS,
    constraint books_authors_BOOKS_ID_fk
        foreign key (book_id) references BOOKS on delete cascade
);


insert into AUTHORS (FIRST_NAME, LAST_NAME)values
('author_1_first_name', 'author_1_last_name'),('author_2_first_name', 'author_2_last_name'),
('author_3_first_name', 'author_3_last_name'), ('author_4_first_name', 'author_4_last_name');

insert into GENRES (NAME)values
('genre_1'), ('genre_2'), ('genre_3'), ('genre_4');

insert into BOOKS (GENRE_ID, TITLE) values
(1, 'title_1'), (2, 'title_2'),
(3, 'title_3');

insert into BOOKS_AUTHORS (BOOK_ID, AUTHOR_ID)
values (1, 1),(1, 2), (1, 3), (2, 1), (3, 3);
