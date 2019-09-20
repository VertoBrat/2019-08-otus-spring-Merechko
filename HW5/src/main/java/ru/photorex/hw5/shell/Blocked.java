package ru.photorex.hw5.shell;

import org.springframework.shell.Availability;

public interface Blocked {

    void unBlock();

    boolean isLogged();

    default public Availability availabilityCheck() {
        return isLogged() ? Availability.available() : Availability.unavailable("you are not log in");
    }
}
