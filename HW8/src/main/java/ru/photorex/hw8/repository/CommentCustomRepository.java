package ru.photorex.hw8.repository;

public interface CommentCustomRepository {

    void removeCommentsOfDeletedBook(String bookId);
}
