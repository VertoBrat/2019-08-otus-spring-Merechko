package ru.photorex.hw5.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;
import ru.photorex.hw5.service.UserDetailService;

import javax.validation.constraints.Size;
import java.util.List;

@ShellComponent
@RequiredArgsConstructor
public class CommandsForLibrary {

    private static final String SUCCESS_LOGIN = System.lineSeparator() + "You logged in successfully!!!";
    private static final String LOGOUT = System.lineSeparator() + "You log out from application";

    private final UserDetailService userDetailService;
    private final List<Blocked> shellComponents;

    private boolean isLogged = false;

    @ShellMethod(value = "Login in application.", key = {"l", "login"})
    @ShellMethodAvailability("availabilityCheckLogout")
    public String login(@ShellOption({"-N", "--name"}) @Size(min = 1, max = 5) String name) {
        return manageAccess(name, true);
    }

    @ShellMethod(value = "Logout from application.", key = {"lo", "logout"})
    @ShellMethodAvailability("availabilityCheckLogin")
    public String logout() {
        return manageAccess(null, false);
    }

    public Availability availabilityCheckLogin() {
        return isLogged ? Availability.available() : Availability.unavailable("you are not log in");
    }

    public Availability availabilityCheckLogout() {
        return !isLogged ? Availability.available() : Availability.unavailable("you are not log out");
    }

    private String manageAccess(String name, boolean isLogged) {
        userDetailService.setUserName(name);
        shellComponents.forEach(Blocked::changeAccess);
        this.isLogged = isLogged;
        return isLogged?SUCCESS_LOGIN:LOGOUT;
    }
}
