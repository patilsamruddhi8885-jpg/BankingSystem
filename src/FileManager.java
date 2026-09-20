import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

public class FileManager {

    private static final String DATA_FOLDER = "data";
    private static final String ACCOUNTS_FILE = "data/accounts.txt";
    private static final String TRANSACTIONS_FILE = "data/transactions.txt";

    // Create data folder and accounts file
    public static void initializeFiles() {

        try {

            // Create data folder
            File folder = new File(DATA_FOLDER);

            if (!folder.exists()) {
                folder.mkdir();
            }

            // Create accounts file
            File accountsFile = new File(ACCOUNTS_FILE);

            if (!accountsFile.exists()) {
                accountsFile.createNewFile();
            }

            // Create transactions file
            File transactionsFile = new File(TRANSACTIONS_FILE);

            if (!transactionsFile.exists()) {
                transactionsFile.createNewFile();
            }

            System.out.println("Data files initialized successfully.");

        } catch (IOException e) {

            System.out.println("Error creating data files.");
        }
    }

    // Save account
        public static void saveAccount(Account account) {

        try {

            FileWriter writer = new FileWriter(
                    ACCOUNTS_FILE,
                    true
            );

            writer.write(
                    account.getAccountNumber() + "|" +
                    account.getAccountHolderName() + "|" +
                    account.getPhoneNumber() + "|" +
                    account.getPin() + "|" +
                    account.getBalance() + "\n"
            );

            writer.close();

        } catch (IOException e) {

            System.out.println("Error saving account.");
        }
    }

    // Save all accounts
    public static void saveAllAccounts(Bank bank) {

        try {

            FileWriter writer = new FileWriter(ACCOUNTS_FILE);

            for (Account account : bank.getAccounts()) {

                writer.write(
                        account.getAccountNumber() + "|" +
                        account.getAccountHolderName() + "|" +
                        account.getPhoneNumber() + "|" +
                        account.getPin() + "|" +
                        account.getBalance() + "\n"
                );
            }

            writer.close();

        } catch (IOException e) {

            System.out.println("Error updating account data.");
        }
    }

    public static void saveTransaction(
        String accountNumber,
        Transaction transaction) {

        try {

            FileWriter writer =
                    new FileWriter(TRANSACTIONS_FILE, true);

            writer.write(
                    accountNumber + "|" +
                    transaction.getType() + "|" +
                    transaction.getAmount() + "|" +
                    transaction.getBalance() + "|" +
                    transaction.getDescription() + "|" +
                    transaction.getDateTime() + "\n"
            );

            writer.close();

        } catch (IOException e) {

            System.out.println("Error saving transaction.");
        }
    }
    // Load accounts from file
    public static void loadAccounts(Bank bank) {

        try {

            BufferedReader reader =
                    new BufferedReader(new FileReader(ACCOUNTS_FILE));

            String line;

            while ((line = reader.readLine()) != null) {

                String[] data = line.split("\\|");

                if (data.length == 5) {

                    String accountNumber = data[0];
                    String accountHolderName = data[1];
                    String phoneNumber = data[2];
                    String pin = data[3];
                    double balance = Double.parseDouble(data[4]);

                    // Create account object
                    Account account = new Account(
                            accountNumber,
                            accountHolderName,
                            phoneNumber,
                            pin,
                            balance
                    );

                    // Add account to bank
                    bank.addAccount(account);

                    // Update account counter
                    try {

                        int number = Integer.parseInt(accountNumber);

                        if (number >= Main.accountCounter) {
                            Main.accountCounter = number + 1;
                        }

                    } catch (NumberFormatException e) {

                        System.out.println(
                                "Invalid account number: " + accountNumber
                        );
                    }
                }
            }

            reader.close();

        } catch (IOException e) {

            System.out.println("Error loading accounts.");

        } catch (NumberFormatException e) {

            System.out.println("Invalid account data found.");
        }
    }

    //loadTransactions()
    public static void loadTransactions(Bank bank) {

    try {

        BufferedReader reader =
                new BufferedReader(
                        new FileReader(TRANSACTIONS_FILE)
                );

        String line;

        while ((line = reader.readLine()) != null) {

            String[] data = line.split("\\|", 6);

            if (data.length == 6) {

                String accountNumber = data[0];
                String type = data[1];
                double amount = Double.parseDouble(data[2]);
                double balance = Double.parseDouble(data[3]);
                String description = data[4];
                LocalDateTime dateTime =
                        LocalDateTime.parse(data[5]);

                Account account =
                        bank.findAccount(accountNumber);

                if (account != null) {

                    Transaction transaction =
                            new Transaction(
                                    type,
                                    amount,
                                    balance,
                                    description,
                                    dateTime
                            );

                    account.addExistingTransaction(transaction);
                }
            }
        }

        reader.close();

    } catch (IOException e) {

        System.out.println("Error loading transactions.");

    } catch (NumberFormatException e) {

        System.out.println("Invalid transaction data found.");

    } catch (Exception e) {

        System.out.println("Error reading transaction data.");
    }
}
}