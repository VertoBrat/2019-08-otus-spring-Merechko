package ru.photorex.hw8.repository;

public interface BookCustomRepository {

    void removeCommentsFromArrayById(String id);

    void addCommentToArray(String commentId, String bookId);
}