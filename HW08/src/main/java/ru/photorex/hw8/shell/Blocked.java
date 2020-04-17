package ru.photorex.hw8.shell;

import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellMethodAvailability;

public interface Blocked {

    void changeAccess();

    boolean isLogged();

    @ShellMethodAvailability
    default Availability availabilityCheck() {
        return isLogged() ? Availability.available() : Availability.unavailable("you are not log in");
    }
}
