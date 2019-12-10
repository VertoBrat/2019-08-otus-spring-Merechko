package ru.photorex.hw15.utils;

import java.util.concurrent.TimeUnit;

public class SafetySleepHelper {

    public static void sleepWithoutTryCatch(long sleepFor, TimeUnit unit) {
        boolean interrupted = false;
        try {
            unit.sleep(sleepFor);
        } catch (InterruptedException e) {
            interrupted = true;
        } finally {
            if (interrupted) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
