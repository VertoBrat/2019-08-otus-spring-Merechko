package ru.photorex.hw4.services;

import ru.photorex.hw4.utils.Messages;

public interface LocaleService {

    String getMessage(Messages property);

    String getMessage(Messages property, String[] args);

    void setLocale(String locale);
}
