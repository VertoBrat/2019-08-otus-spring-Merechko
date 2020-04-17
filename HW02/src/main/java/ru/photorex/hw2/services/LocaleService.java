package ru.photorex.hw2.services;


import ru.photorex.hw2.utils.Messages;

public interface LocaleService {

    String getMessage(Messages property);

    String getMessage(Messages property, String[] args);

    void setLocale(String locale);
}
