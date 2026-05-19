package main;

import util.FileLogger;

// One place to format + log any exception that escapes a handler
public class GlobalExceptionHandler {

    public static void handle(Exception e) {

        // Console: short, user-facing message.
        System.out.println(
                "ERROR: " + e.getMessage()
        );

        // File: exception class + message, so error.log shows the type too.
        FileLogger.logError(
                e.getClass().getSimpleName()
                + " - "
                + e.getMessage()
        );
    }
}