package ru.photorex.hw6.shell;

import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;
import ru.photorex.hw6.service.UserDetailService;

import javax.validation.constraints.Size;
import java.util.List;

@ShellComponent
public class CommandsForAccess extends LibraryCommands {

    private static final String SUCCESS_LOGIN = System.lineSeparator() + "You logged in successfully!!!";
    private static final String LOGOUT = System.lineSeparator() + "You log out from application";
    private static final String NEGATIVE_LOGOUT_REASON = "you are not log out";

    private final UserDetailService userDetailService;
    private final List<Blocked> shellComponents;

    public CommandsForAccess(UserDetailService userDetailService, List<Blocked> shellComponents) {
        this.userDetailService = userDetailService;
        this.shellComponents = shellComponents;
        this.shellComponents.add(this);
    }

    @ShellMethod(value = "Login in application.", key = {"l", "login"})
    @ShellMethodAvailability("availabilityCheckLogout")
    public String login(@ShellOption({"-N", "--name"}) @Size(min = 1, max = 5) String name) {
        return manageAccess(name);
    }

    @ShellMethod(value = "Logout from application.", key = {"lo", "logout"})
    public String logout() {
        return manageAccess(null);
    }

    public Availability availabilityCheckLogout() {
        return !isLogged() ? Availability.available() : Availability.unavailable(NEGATIVE_LOGOUT_REASON);
    }

    private String manageAccess(String name) {
        userDetailService.setUserName(name);
        shellComponents.forEach(Blocked::changeAccess);
        return isLogged() ? SUCCESS_LOGIN : LOGOUT;
    }
}
