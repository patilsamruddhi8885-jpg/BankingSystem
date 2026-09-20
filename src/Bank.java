import java.util.ArrayList;

public class Bank {

    private ArrayList<Account> accounts = new ArrayList<>();


    // Add account
    public void addAccount(Account account) {
        accounts.add(account);
    }

    // Get all accounts
    public ArrayList<Account> getAccounts() {
        return accounts;
    }

    // Find account using account number
    public Account findAccount(String accountNumber) {

        for (Account account : accounts) {

            if (account.getAccountNumber().equals(accountNumber)) {
                return account;
            }
        }

        return null;
    }

    // Display all accounts
            public void displayAllAccounts() {

            if (accounts.isEmpty()) {
                System.out.println("\nNo accounts available.");
                return;
            }

            System.out.println("\n========================== ALL ACCOUNTS ==========================");

            System.out.printf(
                    "%-15s %-25s %-15s %s%n",
                    "Account No.",
                    "Account Holder",
                    "Phone",
                    "Balance"
            );

            System.out.println(
                    "------------------------------------------------------------------"
            );

            for (Account account : accounts) {

                System.out.printf(
                        "%-15s %-25s %-15s ₹%.2f%n",
                        account.getAccountNumber(),
                        account.getAccountHolderName(),
                        account.getPhoneNumber(),
                        account.getBalance()
                );
            }

            System.out.println(
                    "------------------------------------------------------------------"
            );
        }

        public void displayAccountStatistics() {

        int totalAccounts = accounts.size();
        double totalBalance = 0;

        for (Account account : accounts) {
            totalBalance += account.getBalance();
        }

        System.out.println("\n================================");
        System.out.println("       ACCOUNT STATISTICS");
        System.out.println("================================");
        System.out.println("Total Accounts : " + totalAccounts);
        System.out.println("Total Balance  : ₹" + totalBalance);
    }
}