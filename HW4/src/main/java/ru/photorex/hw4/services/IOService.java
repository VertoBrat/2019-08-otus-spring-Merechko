package ru.photorex.hw4.services;

public interface IOService {

    String readString();

    void printString(String s);

    void printFormattedString(String s, String name, int result);
}
