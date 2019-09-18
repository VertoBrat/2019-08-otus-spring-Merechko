drop table if exists books;
drop table if exists authors;
drop table if exists GENRES;

create table AUTHORS
(
    ID BIGINT auto_increment,
    FIRST_NAME VARCHAR(255) not null,
    LAST_NAME  VARCHAR(255) not null,
    constraint AUTHORS_PK
        primary key (ID)
);
create table GENRES
(
    ID BIGINT auto_increment,
    NAME VARCHAR(255) not null
        constraint GENRES_NAME_UINDEX
        unique,
    constraint GENRES_PK
        primary key (ID)
);
create table BOOKS
(
    ID        BIGINT auto_increment,
    TITLE     VARCHAR(255) not null,
    AUTHOR_ID BIGINT       not null,
    GENRE_ID  BIGINT       not null,
    constraint BOOKS_PK
        primary key (ID),
    constraint BOOKS_AUTHORS_ID_FK
        foreign key (AUTHOR_ID) references AUTHORS,
    constraint BOOKS_GENRES_ID_FK
        foreign key (GENRE_ID) references GENRES
);

