package main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// One place to format + log exceptions
public class GlobalExceptionHandler {

    // Logger object
    private static final Logger logger =
            LoggerFactory.getLogger(GlobalExceptionHandler.class);

    public static void handle(Exception e) {

        // User-friendly console message
        System.out.println(
                "ERROR: " + e.getMessage()
        );

        // Professional logging
        logger.error(
                "Exception occurred: {} - {}",
                e.getClass().getSimpleName(),
                e.getMessage()
        );
    }
}