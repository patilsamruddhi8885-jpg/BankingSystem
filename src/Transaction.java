import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transaction {

    private String type;
    private double amount;
    private double balance;
    private String description;
    private LocalDateTime dateTime;

    // Constructor for new transaction
    public Transaction(String type, double amount,
                       double balance, String description) {

        this.type = type;
        this.amount = amount;
        this.balance = balance;
        this.description = description;
        this.dateTime = LocalDateTime.now();
    }

        // Constructor for loading existing transaction
        public Transaction(String type, double amount,
                        double balance, String description,
                        LocalDateTime dateTime) {

        this.type = type;
        this.amount = amount;
        this.balance = balance;
        this.description = description;
        this.dateTime = dateTime;
        }

    // Display transaction
    public void displayTransaction() {

        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

        System.out.println(
                String.format(
                        "%-12s ₹%-10.2f ₹%-12.2f %-20s %s",
                        type,
                        amount,
                        balance,
                        description,
                        dateTime.format(formatter)
                )
        );
    }
        // Get transaction type
        public String getType() {
        return type;
        }

        // Get transaction amount
        public double getAmount() {
        return amount;
        }

        // Get balance after transaction
        public double getBalance() {
        return balance;
        }

        // Get description
        public String getDescription() {
        return description;
        }

        // Get date and time
        public LocalDateTime getDateTime() {
        return dateTime;
        }
}