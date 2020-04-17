package ru.photorex.hw2.plain.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.photorex.hw2.exceptions.NoCsvDataException;
import ru.photorex.hw2.model.Question;
import ru.photorex.hw2.services.LocaleService;
import ru.photorex.hw2.services.QuestionDataParserImpl;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Сервис для работы с фаилом")
@ExtendWith(MockitoExtension.class)
public class QuestionDataParserImplTest {

    private static final String PATH = "data.csv";
    private static final String INVALID_PATH = "";
    private static final int COUNT_OF_QUESTIONS = 5;

    @Mock
    private LocaleService localeService;

    private QuestionDataParserImpl parser;

    @BeforeEach
    void setUp() {
        parser = new QuestionDataParserImpl(localeService);
    }

    @Test
    @DisplayName("должен возвращать список вопросов из фаила")
    void shouldReturnListOfQuestionsFromFile() {
        List<Question> questions = parser.parseQuestions(PATH);
        assertThat(questions).hasSize(COUNT_OF_QUESTIONS);
    }

    @Test
    @DisplayName("должен бросать исключение при неправильном пути до фаила")
    void shouldThrowDataExceptionIfPathIsInvalid() {
        assertThrows(NoCsvDataException.class, () -> parser.parseQuestions(INVALID_PATH));
    }
}
