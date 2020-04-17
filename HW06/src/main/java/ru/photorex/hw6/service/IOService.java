package ru.photorex.hw6.service;

public interface IOService {

    String readString();

    void printString(String s);

    void printFormattedString(String s, String name, int result);
}
