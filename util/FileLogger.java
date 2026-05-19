package util;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class FileLogger {

    private static final String FILE_NAME = "error.log";

    public static void logError(String errorMessage) {

        try (
                FileWriter fw = new FileWriter(FILE_NAME, true);
                PrintWriter pw = new PrintWriter(fw)
        ) {

            pw.println("[ERROR] " + errorMessage);

        } catch (IOException e) {

            // Logger can't crash the app: fall back to stderr so the
            // failure is still visible to the developer.
            System.out.println(
                    "Failed to write log: " + e.getMessage()
            );
        }
    }
}