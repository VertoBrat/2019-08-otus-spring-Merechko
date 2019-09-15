package ru.photorex.hw4.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Сервис для работы с результатами")
@SpringBootTest
public class PollMaxResultServiceImplTest {

    private static final String USER1 = "Dima";
    private static final int MAX_RESULT_USER1 = 3;
    private static final int COUNT_OF_RECORDS = 3;
    private static final String NEW_USER = "User";
    private static final int COUNT_OF_RIGHT_ANSWERS_FOR_NEW_USER = 3;
    private static final int COUNT_OF_RIGHT_ANSWERS_FOR_OLD_USER = 0;

    @Autowired
    private PollMaxResultService service;

    @Test
    @DisplayName("должен возвращать список с тремя записями")
    void shouldReturnListWithThreeRecords() {
        assertThat(service.getMaxResultList()).hasSize(COUNT_OF_RECORDS);
    }

    @Test
    @DisplayName("должен добавлять запись в список")
    void shouldAddResultToList() {
        service.addResult(NEW_USER, COUNT_OF_RIGHT_ANSWERS_FOR_NEW_USER);
        assertThat(service.getMaxResultList()).hasSize(COUNT_OF_RECORDS + 1);
    }

    @Test
    @DisplayName("не должен дублировать пользователей в списке")
    void shouldNotAddAdditionalRecordToList() {
        service.addResult(USER1, COUNT_OF_RIGHT_ANSWERS_FOR_OLD_USER);
        assertThat(service.getMaxResultList()).hasSize(COUNT_OF_RECORDS);
    }

    @Test
    @DisplayName("не должен обновлять запись в списке")
    void shouldNotUpdateRecordInList() {
        service.addResult(USER1, COUNT_OF_RIGHT_ANSWERS_FOR_OLD_USER);
        assertThat(true).isEqualTo(
                service.getMaxResultList().stream()
                        .anyMatch(r ->
                                r.getUserName().equals(USER1) && r.getMaxResult().equals(MAX_RESULT_USER1)));
    }

    @Test
    @DisplayName("должен обновить запись в списке")
    void shouldUpdateRecordInList() {
        service.addResult(USER1, COUNT_OF_RIGHT_ANSWERS_FOR_OLD_USER + 4);
        assertThat(true).isEqualTo(
                service.getMaxResultList().stream()
                        .anyMatch(r ->
                                r.getUserName().equals(USER1) && r.getMaxResult().equals(MAX_RESULT_USER1 + 1)));
    }
}
