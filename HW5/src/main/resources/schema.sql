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
        primary key (ID)
);

create table BOOKS_AUTHORS
(
    BOOK_ID   long not null,
    AUTHOR_ID long not null,
    constraint books_authors_AUTHORS_ID_fk
        foreign key (author_id) references AUTHORS,
    constraint books_authors_BOOKS_ID_fk
        foreign key (book_id) references BOOKS
);
