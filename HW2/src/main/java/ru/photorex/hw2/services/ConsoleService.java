package ru.photorex.hw2.services;

import lombok.RequiredArgsConstructor;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

@RequiredArgsConstructor
public class ConsoleService implements IOService {

    private final InputStream in;
    private final PrintStream out;

    @Override
    public String readString() {
        Scanner sc = new Scanner(in);
        return sc.nextLine();
    }

    @Override
    public void printString(String s) {
        out.println(s);
    }
}
