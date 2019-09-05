package ru.photorex.hw2.services;

import java.util.Locale;

public interface LocaleService {

    String getMessage(String message);

    String getMessage(String message, String[] args);

    void setLocale(Locale locale);
}
