import java.util.Scanner;

public class Main {

    static Scanner scanner = new Scanner(System.in);

    // Account number counter
    static int accountCounter = 100001;

    
    
    public static void main(String[] args) {

        FileManager.initializeFiles();

        Bank bank = new Bank();


        FileManager.loadAccounts(bank);

        FileManager.loadTransactions(bank);

        while (true) {

            System.out.println("\n================================");
            System.out.println("        BANKING SYSTEM");
            System.out.println("================================");
            System.out.println("1. Create Account");
            System.out.println("2. Customer Login");
            System.out.println("3. Admin Login");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");

            int choice = InputValidator.getInt(scanner);

            switch(choice) {

                case 1:
                    createAccount(bank);
                    break;

                case 2:
                    login(bank);
                    break;

                case 3:
                    adminLogin(bank);
                    break;

                case 4:
                    System.out.println("Thank you for using the Banking System!");
                    return;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    // ==============================
    // CREATE ACCOUNT
    // ==============================

    public static void createAccount(Bank bank) {

        System.out.println("\n================================");
        System.out.println("        CREATE ACCOUNT");
        System.out.println("================================");

        String name;

        while (true) {

            System.out.print("Enter account holder name: ");
            name = scanner.nextLine().trim();

            if (!name.isEmpty()) {
                break;
            }

            System.out.println("Name cannot be empty. ❌");
        }

        String phone;

        while (true) {

            System.out.print("Enter phone number: ");
            phone = scanner.nextLine();

            if (phone.matches("\\d{10}")) {
                break;
            }

            System.out.println("Phone number must contain exactly 10 digits. ❌");
        }

        
        String pin;

        while (true) {

            System.out.print("Create 4-digit PIN: ");
            pin = scanner.nextLine();

            if (pin.matches("\\d{4}")) {
                break;
            }

            System.out.println("PIN must contain exactly 4 digits. ❌");
        }

        double initialDeposit;

        while (true) {

            System.out.print("Enter initial deposit: ₹");
            initialDeposit = InputValidator.getDouble(scanner);

            if (initialDeposit >= 0) {
                break;
            }

            System.out.println("Initial deposit cannot be negative. ❌");
        }

        // Generate account number
        String accountNumber = String.valueOf(accountCounter++);

        // Create account object
        Account account = new Account(
                        accountNumber,
                        name,
                        phone,
                        pin,
                        initialDeposit
        );

        // Store account in bank
        bank.addAccount(account);

        // Save account to file
        FileManager.saveAccount(account);

        System.out.println("\n================================");
        System.out.println("    ACCOUNT CREATED SUCCESSFULLY");
        System.out.println("================================");

        System.out.println("Account Number : " + accountNumber);
        System.out.println("Account Holder : " + name);
        System.out.println("Balance        : ₹" + initialDeposit);
    }

    // ==============================
    // LOGIN
    // ==============================

    public static void login(Bank bank) {

        System.out.println("\n================================");
        System.out.println("             LOGIN");
        System.out.println("================================");

        System.out.print("Enter Account Number: ");
        String accountNumber = scanner.nextLine();

        System.out.print("Enter PIN: ");
        String pin = scanner.nextLine();

        // Find account
        Account account = bank.findAccount(accountNumber);

        if (account == null) {

            System.out.println("\nAccount not found.");

        } else if (!account.checkPin(pin)) {

            System.out.println("\nIncorrect PIN.");

        } else {

            System.out.println("\n================================");
            System.out.println("       LOGIN SUCCESSFUL");
            System.out.println("================================");

            System.out.println("Welcome, " + account.getAccountHolderName() + "!");

            customerMenu(bank, account);
        }
    }

    // ==============================
    // CUSTOMER MENU
    // ==============================

    public static void customerMenu(Bank bank, Account account) {

        while (true) {

            System.out.println("\n================================");
            System.out.println("        CUSTOMER MENU");
            System.out.println("================================");

            System.out.println("1. Check Balance");
            System.out.println("2. Deposit Money");
            System.out.println("3. Withdraw Money");
            System.out.println("4. Transfer Money");
            System.out.println("5. Transaction History");
            System.out.println("6. Account Details");
            System.out.println("7. Change PIN");
            System.out.println("8. Logout");

            System.out.print("Enter your choice: ");

            System.out.print("Enter your choice: ");

            int choice = InputValidator.getInt(scanner);

            switch (choice) {

                case 1:
                    System.out.println(
                            "Current Balance: ₹" + account.getBalance()
                    );
                    break;

               case 2:
                    depositMoney(bank, account);
                    break;

                case 3:
                    withdrawMoney(bank, account);
                    break;

                case 4:
                    transferMoney(bank, account);
                    break;  

                case 5:
                    account.displayTransactionHistory();
                    break;

                case 6:
                    System.out.println("Account Details:");
                    System.out.println(
                            "Account Number : " + account.getAccountNumber()
                    );
                    System.out.println(
                            "Account Holder : " + account.getAccountHolderName()
                    );
                    System.out.println(
                            "Phone Number   : " + account.getPhoneNumber()
                    );
                    System.out.println(
                            "Balance        : ₹" + account.getBalance()
                    );
                    break;

                case 7:
                    changePin(bank, account);
                    break;

                case 8:
                    System.out.println("\nLogged out successfully.");
                    return;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
    // ==============================
    // DEPOSIT MONEY
    // ==============================

    public static void depositMoney(Bank bank, Account account) {

            System.out.println("\n================================");
            System.out.println("        DEPOSIT MONEY");
            System.out.println("================================");

            System.out.print("Enter amount to deposit: ₹");

            double amount = InputValidator.getDouble(scanner);

            if (amount <= 0) {

                System.out.println("Invalid amount. Deposit must be greater than ₹0.");

            } else {

                account.deposit(amount);

                Transaction transaction = account.addTransaction(
                        "DEPOSIT",
                        amount,
                        "Money deposited"
                );

                FileManager.saveTransaction(
                        account.getAccountNumber(),
                        transaction
                );

                FileManager.saveAllAccounts(bank);

                System.out.println("\nDeposit successful! ✅");
                System.out.println("Amount Deposited : ₹" + amount);
                System.out.println("New Balance      : ₹" + account.getBalance());
        }
    }
    // ==============================
    // WITHDRAW MONEY
    // ==============================

        public static void withdrawMoney(Bank bank, Account account) {

                System.out.println("\n================================");
                System.out.println("        WITHDRAW MONEY");
                System.out.println("================================");

                System.out.print("Enter amount to withdraw: ₹");

                double amount = InputValidator.getDouble(scanner);

                if (amount <= 0) {

                    System.out.println("Invalid amount. Withdrawal must be greater than ₹0.");

                } else if (amount > account.getBalance()) {

                    System.out.println("Insufficient balance! ❌");
                    System.out.println("Available Balance: ₹" + account.getBalance());

                } else {

                    account.withdraw(amount);

                    Transaction transaction = account.addTransaction(
                            "WITHDRAW",
                            amount,
                            "Money withdrawn"
                    );

                    FileManager.saveTransaction(
                            account.getAccountNumber(),
                            transaction
                    );

                    FileManager.saveAllAccounts(bank);

                    System.out.println("\nWithdrawal successful! ✅");
                    System.out.println("Amount Withdrawn : ₹" + amount);
                    System.out.println("Remaining Balance: ₹" + account.getBalance());
            }
        }
    // ==============================
    // TRANSFER MONEY
    // ==============================

    public static void transferMoney(Bank bank, Account sender) {

            System.out.println("\n================================");
            System.out.println("        TRANSFER MONEY");
            System.out.println("================================");

            System.out.print("Enter receiver account number: ");
            String receiverAccountNumber = scanner.nextLine();

            // Find receiver account
            Account receiver = bank.findAccount(receiverAccountNumber);

            // Check whether receiver exists
            if (receiver == null) {

                System.out.println("Receiver account not found. ❌");
                return;
            }

            // Prevent self-transfer
            if (sender.getAccountNumber().equals(receiverAccountNumber)) {

                System.out.println("You cannot transfer money to your own account. ❌");
                return;
            }

            System.out.print("Enter amount to transfer: ₹");
            
            double amount = InputValidator.getDouble(scanner);

            // Validate amount
            if (amount <= 0) {

                System.out.println("Invalid amount. ❌");
                return;
            }

            // Check balance
            if (amount > sender.getBalance()) {

                System.out.println("Insufficient balance! ❌");
                System.out.println("Available Balance: ₹" + sender.getBalance());
                return;
            }

            // Transfer money
           sender.withdraw(amount);
           receiver.deposit(amount);

            Transaction senderTransaction = sender.addTransaction(
                    "TRANSFER",
                    amount,
                    "Transfer to " + receiver.getAccountNumber()
            );

            Transaction receiverTransaction = receiver.addTransaction(
                    "TRANSFER",
                    amount,
                    "Received from " + sender.getAccountNumber()
            );

            FileManager.saveTransaction(
                    sender.getAccountNumber(),
                    senderTransaction
            );

            FileManager.saveTransaction(
                    receiver.getAccountNumber(),
                    receiverTransaction
            );

            FileManager.saveAllAccounts(bank);

            System.out.println("\n================================");
            System.out.println("      TRANSFER SUCCESSFUL");
            System.out.println("================================");

            System.out.println("Amount Transferred : ₹" + amount);
            System.out.println("To Account        : " + receiver.getAccountNumber());
            System.out.println("Receiver Name     : " + receiver.getAccountHolderName());
            System.out.println("Your New Balance  : ₹" + sender.getBalance());
     }

    // ==============================
    // CHANGE PIN
    // ==============================

         public static void changePin(Bank bank, Account account)  {

            System.out.println("\n================================");
            System.out.println("           CHANGE PIN");
            System.out.println("================================");

            System.out.print("Enter current PIN: ");
            String currentPin = scanner.nextLine();

            // Check current PIN
            if (!account.checkPin(currentPin)) {

                System.out.println("Incorrect current PIN. ❌");
                return;
            }

            System.out.print("Enter new 4-digit PIN: ");
            String newPin = scanner.nextLine();

            // Check PIN length
            if (newPin.length() != 4) {

                System.out.println("PIN must contain exactly 4 digits. ❌");
                return;
            }

            // Check whether PIN contains only digits
            if (!newPin.matches("\\d{4}")) {

                System.out.println("PIN must contain only numbers. ❌");
                return;
            }

            // Change PIN
           account.changePin(newPin);

            FileManager.saveAllAccounts(bank);

            System.out.println("\nPIN changed successfully! ✅");
        }

        //temporary Admin Login

        public static void adminLogin(Bank bank) {

        System.out.println("\n================================");
        System.out.println("          ADMIN LOGIN");
        System.out.println("================================");

        System.out.print("Enter Admin Username: ");
        String username = scanner.nextLine();

        System.out.print("Enter Admin Password: ");
        String password = scanner.nextLine();

        if (username.equals("admin") && password.equals("admin123")) {

            System.out.println("\nAdmin Login Successful! ✅");

            adminMenu(bank);

        } else {

            System.out.println("\nInvalid Admin Username or Password.");
        }
     }

        //adminMenu() 

        public static void adminMenu(Bank bank) {

        while (true) {

            System.out.println("\n================================");
            System.out.println("         ADMIN DASHBOARD");
            System.out.println("================================");
            System.out.println("1. View All Accounts");
            System.out.println("2. Search Account");
            System.out.println("3. View Transaction History");
            System.out.println("4. Account Statistics");
            System.out.println("5. Logout");
            System.out.println("================================");
            System.out.print("Enter your choice: ");

            int choice = InputValidator.getInt(scanner);

            switch (choice) {

                case 1:
                    bank.displayAllAccounts();
                    break;

                case 2:
                    searchAccount(bank);
                    break;

                case 3:
                    viewCustomerTransactions(bank);
                    break;

                case 4:
                    bank.displayAccountStatistics();
                    break;

                case 5:
                    System.out.println("\nAdmin logged out successfully.");
                    return;

                default:
                    System.out.println("\nInvalid choice.");
            }
        }
    }

        //searchAccount()
        public static void searchAccount(Bank bank) {

        System.out.println("\n================================");
        System.out.println("         SEARCH ACCOUNT");
        System.out.println("================================");

        System.out.print("Enter Account Number: ");
        String accountNumber = scanner.nextLine();

        Account account = bank.findAccount(accountNumber);

        if (account == null) {

            System.out.println("\nAccount not found.");

        } else {

            System.out.println("\nAccount Found! ✅");
            System.out.println("--------------------------------");
            System.out.println("Account Number : " +
                    account.getAccountNumber());
            System.out.println("Account Holder : " +
                    account.getAccountHolderName());
            System.out.println("Phone Number   : " +
                    account.getPhoneNumber());
            System.out.println("Balance        : ₹" +
                    account.getBalance());
        }
    }

    //viewCustomerTransactions()

        public static void viewCustomerTransactions(Bank bank) {

        System.out.println("\n================================");
        System.out.println("    CUSTOMER TRANSACTION HISTORY");
        System.out.println("================================");

        System.out.print("Enter Account Number: ");
        String accountNumber = scanner.nextLine();

        Account account = bank.findAccount(accountNumber);

        if (account == null) {

            System.out.println("\nAccount not found.");

        } else {

            System.out.println("\nAccount: " +
                    account.getAccountNumber());

            System.out.println("Holder: " +
                    account.getAccountHolderName());

            account.displayTransactionHistory();
        }
    }

}
