import java.util.Scanner;

public class InputValidator {

    // Read integer safely
    public static int getInt(Scanner scanner) {

        while (true) {

            try {

                return Integer.parseInt(scanner.nextLine());

            } catch (NumberFormatException e) {

                System.out.print("Invalid input. Please enter a number: ");
            }
        }
    }

    // Read double safely
    public static double getDouble(Scanner scanner) {

        while (true) {

            try {

                return Double.parseDouble(scanner.nextLine());

            } catch (NumberFormatException e) {

                System.out.print("Invalid amount. Please enter a valid number: ");
            }
        }
    }
}