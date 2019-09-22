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
