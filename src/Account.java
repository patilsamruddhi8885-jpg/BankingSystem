import java.util.ArrayList;

public class Account {

    private String accountNumber;
    private String accountHolderName;
    private String phoneNumber;
    private String pin;
    private double balance;

    private ArrayList<Transaction> transactions = new ArrayList<>();

    // Constructor:A constructor is used when you create an object.
    public Account(String accountNumber, String accountHolderName,
                   String phoneNumber, String pin, double balance) {

        this.accountNumber = accountNumber;
        this.accountHolderName = accountHolderName;
        this.phoneNumber = phoneNumber;
        this.pin = pin;
        this.balance = balance;
    }

    // Getter for account number
    public String getAccountNumber() {
        return accountNumber;
    }

    // Getter for account holder name
    public String getAccountHolderName() {
        return accountHolderName;
    }

    // Getter for phone number
    public String getPhoneNumber() {
        return phoneNumber;
    }

    // Getter for balance
    public double getBalance() {
        return balance;
    }

    // Check PIN
    public boolean checkPin(String enteredPin) {
        return pin.equals(enteredPin);
    }

    // Getter for PIN
    public String getPin() {
        return pin;
    }

    // Deposit money
    public void deposit(double amount) {
        balance = balance + amount;
    }

    // Withdraw money
    public boolean withdraw(double amount) {

        if (amount <= 0 || amount > balance) {
            return false;
        }

        balance = balance - amount;
        return true;
    }

    // Change PIN
    public void changePin(String newPin) {
        pin = newPin;
    }

        // Add transaction
        public Transaction addTransaction(
                String type,
                double amount,
                String description) {

            Transaction transaction =
                    new Transaction(type, amount, balance, description);

            transactions.add(transaction);

            return transaction;
        }
    // Add an existing transaction loaded from file
    public void addExistingTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    // Display transaction history
    public void displayTransactionHistory() {

        if (transactions.isEmpty()) {

            System.out.println("No transactions available.");

            return;
        }

        System.out.println("\n================ TRANSACTION HISTORY ================");

        System.out.printf(
                "%-12s %-12s %-14s %-20s %s%n",
                "Type",
                "Amount",
                "Balance",
                "Description",
                "Date & Time"
        );

        System.out.println(
                "---------------------------------------------------------------"
        );

        for (Transaction transaction : transactions) {

            transaction.displayTransaction();
        }
    }
}