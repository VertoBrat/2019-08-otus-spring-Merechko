package ru.photorex.hw2.spring.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ru.photorex.hw2.AppConfig;
import ru.photorex.hw2.exceptions.NoCsvDataException;
import ru.photorex.hw2.model.Question;
import ru.photorex.hw2.services.QuestionDataParser;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Сервис для работы с фаилом")
@SpringJUnitConfig(AppConfig.class)
public class QuestionDataParserImplTest {

    private static final String PATH_TO_FILE_WITH_QUESTIONS = "data.csv";
    private static final String PATH_TO_FILE_WITHOUT_QUESTIONS = "data_without_questions.csv";
    private static final String INVALID_PATH = "";
    private static final int COUNT_OF_QUESTIONS = 5;

    @Autowired
    private QuestionDataParser parser;

    @Test
    @DisplayName("должен возвращать список вопросов из фаила")
    void shouldReturnListOfQuestionsFromFile() {
        List<Question> questions = parser.parseQuestions(PATH_TO_FILE_WITH_QUESTIONS);
        assertThat(questions).hasSize(COUNT_OF_QUESTIONS);
    }

    @Test
    @DisplayName("должен бросать исключение при неправильном пути до фаила")
    void shouldThrowDataExceptionIfPathIsInvalid() {
        assertThrows(NoCsvDataException.class, () -> parser.parseQuestions(INVALID_PATH));
    }

    @Test
    @DisplayName("должен бросать исключение при отсутствии вопросов в фаиле")
    void shouldThrowDataExceptionIfFileIsEmpty() {
        assertThrows(NoCsvDataException.class, () -> parser.parseQuestions(PATH_TO_FILE_WITHOUT_QUESTIONS));

    }
}
