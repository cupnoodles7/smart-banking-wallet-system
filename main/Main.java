package main;

// Program entry point
public class Main {

    public static void main(String[] args) {

        System.out.println("Smart Banking System Started");

        BankingController controller = new BankingController();
        try {
            controller.run();
        } finally {
            // closes the shared Scanner
            controller.shutdown();
            System.out.println("Smart Banking System Closed");
        }
    }
}
