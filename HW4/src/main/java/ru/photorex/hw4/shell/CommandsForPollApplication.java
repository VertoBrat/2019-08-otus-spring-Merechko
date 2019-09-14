package ru.photorex.hw4.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;
import ru.photorex.hw4.model.Result;
import ru.photorex.hw4.services.PollMaxResultService;
import ru.photorex.hw4.services.PollService;
import ru.photorex.hw4.services.ResultToTableFormatter;
import ru.photorex.hw4.services.UserDetailService;

import javax.validation.constraints.Size;
import java.util.List;

@ShellComponent
@RequiredArgsConstructor
public class CommandsForPollApplication {

    private static final String SUCCESS_LOGIN = "\r\nYou logged in successfully!!!";
    private static final String LOGOUT = "\r\nYou log out from application";

    private final PollService pollService;
    private final UserDetailService userDetailService;
    private final PollMaxResultService maxResultService;
    private final ResultToTableFormatter formatter;

    private boolean isLogged;

    @ShellMethod(value = "Login in application.", key = {"l", "login"})
    public String login(@ShellOption({"-N", "--name"}) @Size(min = 1, max = 5) String name) {
        userDetailService.setUserName(name);
        isLogged = true;
        return SUCCESS_LOGIN;
    }

    @ShellMethod(value = "Logout from application.", key = {"lo", "logout"})
    public String logout() {
        userDetailService.setUserName("");
        isLogged = false;
        return LOGOUT;
    }

    @ShellMethod(value = "Start poll with data from file.", key = {"s", "start", "start-poll"})
    @ShellMethodAvailability("availabilityCheck")
    public void startPoll() {
        pollService.startPollWithData();
    }

    @ShellMethod(value = "Displays on console a table with max results.", key = {"mr", "results", "max-results"})
    public void getTableOfMaxResult() {
        List<Result> results = maxResultService.getMaxResultList();
        formatter.format(results);
    }

    public Availability availabilityCheck() {
        return isLogged? Availability.available() : Availability.unavailable("you are not log in");
    }
}
