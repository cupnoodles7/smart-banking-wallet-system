package main;

import java.util.InputMismatchException;
import java.util.Scanner;

// a single Scanner instance is shared and closed exactly once
public class InputHandler {

    private final Scanner scanner = new Scanner(System.in);

    public String readLine(String prompt) {

        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    public int readInt(String prompt) {

        System.out.print(prompt);

        try {

            int value = scanner.nextInt();
            scanner.nextLine();

            return value;

        } catch (InputMismatchException e) {

        
            scanner.nextLine();
            throw new IllegalArgumentException(
                    "Expected a whole number"
            );
        }
    }

    public double readDouble(String prompt) {

        System.out.print(prompt);

        try {

            double value = scanner.nextDouble();
            scanner.nextLine();

            return value;

        } catch (InputMismatchException e) {

            scanner.nextLine();
            throw new IllegalArgumentException(
                    "Expected numeric value"
            );
        }
    }

    public void close() {
        scanner.close();
    }
}