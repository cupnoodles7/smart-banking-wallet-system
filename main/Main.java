package main;

public class Main {

    public static void main(String[] args) {

        System.out.println("Smart Banking System Started");

        BankingController controller = new BankingController();
        try {
            controller.run();
        } finally {
            controller.shutdown();
            System.out.println("Smart Banking System Closed");
        }
    }
}
