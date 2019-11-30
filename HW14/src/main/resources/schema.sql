drop table if exists BOOKS_AUTHORS;
drop table if exists COMMENTS;
drop table if exists BOOKS;
drop table if exists GENRES;
drop table if exists AUTHORS;
drop table if exists ROLES;
drop table if exists USERS;

create table AUTHORS
(
    ID bigserial
        constraint authors_pk
        primary key,
    FIRST_NAME varchar(255) not null,
    LAST_NAME varchar(255) not null
);

create table GENRES
(
    ID bigserial not null
        constraint genres_pk
            primary key,
    NAME varchar(100)
);

create table BOOKS
(
    ID bigserial not null
        constraint books_pk
            primary key,
    GENRE_ID bigserial       not null
        constraint fk_books_genres
            references genres,
    TITLE varchar(255) not null,
    ISBN varchar(20) not null
);

create table BOOKS_AUTHORS
(
    BOOK_ID bigserial not null
        constraint fk_books_authors_books
            references books,
    AUTHOR_ID bigserial not null
        constraint fk_books_authors_authors
            references authors
);

create table USERS
(
    ID bigserial not null
        constraint users_pk
            primary key,
    USER_NAME varchar(255) not null,
    PASSWORD varchar(255) not null,
    FULL_NAME varchar(255) not null,
    IS_ENABLED boolean default true not null,
    IS_ACCOUNT_NON_EXPIRED boolean default true not null,
    IS_ACCOUNT_NON_LOCKED boolean default true not null,
    IS_CREDENTIALS_NON_EXPIRED boolean default true not null
);

create unique index users_user_name_uindex
    on users (USER_NAME);

create table ROLES
(
    ID bigserial not null
        constraint roles_pk
            primary key,
    ROLE_NAME varchar(100) not null,
    USER_ID bigserial not null
        constraint fk_users_roles
            references users
            on delete cascade
);

create table COMMENTS
(
    ID bigserial not null
        constraint comments_pk
            primary key,
    TEXT varchar(255),
    BOOK_ID bigserial not null
        constraint fk_books_comments
            references books
            on delete cascade,
    USER_ID bigserial not null
        constraint fk_users_comments
            references users,
    DATE_TIME timestamp not null
);


