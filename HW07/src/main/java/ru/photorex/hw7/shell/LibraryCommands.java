package ru.photorex.hw7.shell;

public class LibraryCommands implements Blocked {

    private boolean isLogged;

    @Override
    public void changeAccess() {
        this.isLogged = !isLogged;
    }

    @Override
    public boolean isLogged() {
        return isLogged;
    }
}
