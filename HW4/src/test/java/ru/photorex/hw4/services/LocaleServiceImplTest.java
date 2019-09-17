package ru.photorex.hw4.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.MessageSource;

import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static ru.photorex.hw4.utils.Messages.INPUT_NAME;

@DisplayName("Сервис для работы с локалью")
@SpringBootTest
public class LocaleServiceImplTest {

    private static final String EN_LOCALE = "en";
    private static final String RU_LOCALE = "ru";
    private static final String TEST_EN_STRING = "Enter first and last name";
    private static final String TEST_RU_STRING = "Введите имя и фамилию";

    @Autowired
    private LocaleService ls;

    @MockBean
    private MessageSource ms;

    @Test
    @DisplayName("должен возвращать строку на англ. языке")
    void shouldReturnEnglishStringFromBundle() {
        given(ms.getMessage(eq(INPUT_NAME.getMessage()), any(), eq(Locale.ENGLISH))).willReturn(TEST_EN_STRING);
        ls.setLocale(EN_LOCALE);
        assertThat(TEST_EN_STRING).isEqualTo(ls.getMessage(INPUT_NAME));
    }

    @Test
    @DisplayName("должен возвращать строку на русском языке")
    void shouldReturnRussianStringFromBundle() {
        given(ms.getMessage(eq(INPUT_NAME.getMessage()), any(), eq(new Locale(RU_LOCALE)))).willReturn(TEST_RU_STRING);
        ls.setLocale(RU_LOCALE);
        assertThat(TEST_RU_STRING).isEqualTo(ls.getMessage(INPUT_NAME));
    }
}
