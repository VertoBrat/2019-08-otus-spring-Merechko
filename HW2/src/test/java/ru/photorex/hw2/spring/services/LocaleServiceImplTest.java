package ru.photorex.hw2.spring.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ru.photorex.hw2.AppConfig;
import ru.photorex.hw2.services.LocaleService;

import static ru.photorex.hw2.utils.Messages.*;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Сервис для работы с локалью")
@SpringJUnitConfig(AppConfig.class)
public class LocaleServiceImplTest {

    private static final String EN_LOCALE = "en";
    private static final String RU_LOCALE = "ru";
    private static final String TEST_EN_STRING = "Enter first and last name";
    private static final String TEST_RU_STRING = "Введите имя и фамилию";

    @Autowired
    private LocaleService ls;

    @Test
    @DisplayName("должен возвращать строку на англ. языке")
    void shouldReturnEnglishStringFromBundle() {
        ls.setLocale(EN_LOCALE);
        assertThat(TEST_EN_STRING).isEqualTo(ls.getMessage(INPUT_NAME));
    }

    @Test
    @DisplayName("должен возвращать строку на русском языке")
    void shouldReturnRussianStringFromBundle() {
        ls.setLocale(RU_LOCALE);
        assertThat(TEST_RU_STRING).isEqualTo(ls.getMessage(INPUT_NAME));
    }
}
