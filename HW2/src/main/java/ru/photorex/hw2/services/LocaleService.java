package ru.photorex.hw2.services;


public interface LocaleService {

    String getMessage(String message);

    String getMessage(String message, String[] args);

    void setLocale(String locale);
}
