package ru.photorex.hw14.service;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.InputStream;
import java.io.PrintStream;

@Getter
@Setter
@NoArgsConstructor
public class ConsoleContext {

    private InputStream inputStream;
    private PrintStream printStream;
}
