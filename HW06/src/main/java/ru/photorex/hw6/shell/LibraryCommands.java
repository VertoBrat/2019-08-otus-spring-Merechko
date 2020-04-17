package ru.photorex.hw6.shell;

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
