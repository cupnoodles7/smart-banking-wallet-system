package main;

import util.FileLogger;

public class GlobalExceptionHandler {

    public static void handle(Exception e) {

        System.out.println(
                "ERROR: " + e.getMessage()
        );

        FileLogger.logError(
                e.getClass().getSimpleName()
                + " - "
                + e.getMessage()
        );
    }
}