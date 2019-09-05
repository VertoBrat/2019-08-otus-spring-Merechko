package ru.photorex.hw2.services;

import java.util.Locale;

public interface LocaleService {

    String getMessage(String message);

    void setLocale(Locale locale);
}
