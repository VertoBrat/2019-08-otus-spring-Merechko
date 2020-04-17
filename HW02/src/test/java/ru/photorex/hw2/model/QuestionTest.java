package ru.photorex.hw2.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Конструктор класса")
public class QuestionTest {

    private static final String QUESTION = "question";
    private static final String ANSWER = "answer";

    @Test
    @DisplayName("правильно конфигурирует экземпляр класса")
    void shouldConstructValidQuestion() {
        Question question = new Question();
        question.setTheQuestion(QUESTION);
        question.setAnswer(ANSWER);
        assertThat(question).hasFieldOrPropertyWithValue("theQuestion", QUESTION);
        assertThat(question).hasFieldOrPropertyWithValue("answer", ANSWER);
    }

}
