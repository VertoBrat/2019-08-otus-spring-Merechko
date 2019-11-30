package ru.photorex.hw14.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.ExitStatus;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.photorex.hw14.service.BatchService;
import ru.photorex.hw14.service.IOService;

@ShellComponent
@RequiredArgsConstructor
public class CommandsForJobLaunching {

    private final BatchService service;
    private final IOService ioService;

    @ShellMethod(value = "Display available jobs.", key = {"dj", "d job"})
    public void displayAllJobsName() {
        service.getJobNames().forEach(ioService::printString);
    }

    @ShellMethod(value = "Launch some job by name.", key = {"l"})
    public void runJobByName(@ShellOption({"-n"}) String jobName) {
        ExitStatus status = service.startJobByName(jobName);
        ioService.printString(status.getExitDescription());
    }
}
