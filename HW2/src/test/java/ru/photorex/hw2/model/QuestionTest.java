package ru.photorex.hw2.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

public class QuestionTest {

    private static final String QUESTION = "question";
    private static final String ANSWER = "answer";

    @Test
    void initAndGet() {
        Question question = new Question();
        question.setTheQuestion(QUESTION);
        question.setAnswer(ANSWER);
        assertThat(question).hasFieldOrPropertyWithValue("theQuestion", QUESTION);
        assertThat(question).hasFieldOrPropertyWithValue("answer", ANSWER);
    }

}
