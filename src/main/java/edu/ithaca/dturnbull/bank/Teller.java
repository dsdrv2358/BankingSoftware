package edu.ithaca.dturnbull.bank;

public class Teller extends ATM {

    public Teller(Bank bank) {
        super(bank);
    }

    // Method to create a new user
    public void createUser(String username, String password) {
        User user = new User(username, password);
        bank.addUser(user);
    }

    // Method to delete a user
    public void deleteUser(String username) {
        User user = bank.getUser(username);
        if (user != null && user.getAccounts().isEmpty()) {
            bank.removeUser(username);
        } else {
            System.out.println("User has existing accounts or does not exist.");
        }
    }

    // Method to create a new account for an existing user
    public void createAccount(String username, String password, String accountType) {
        User user = bank.getUser(username);
        if (user != null) {
            Account account;
            if (accountType.equalsIgnoreCase("savings")) {
                account = new Savings(username, password, 0.02); // Example interest rate
            } else if (accountType.equalsIgnoreCase("checkings")) {
                account = new Checkings(username, password);
            } else {
                System.out.println("Invalid account type.");
                return;
            }
            user.addAccount(account);
            bank.addAccount(account);
        } else {
            System.out.println("User does not exist.");
        }
    }

    // Method to delete an account
    public void deleteAccount(String username, long acctNum) {
        User user = bank.getUser(username);
        if (user != null) {
            Account account = user.getAccount(acctNum);
            if (account != null) {
                user.removeAccount(acctNum);
                bank.removeAccount(acctNum);
                if (user.getAccounts().isEmpty()) {
                    bank.removeUser(username);
                }
            } else {
                System.out.println("Account does not exist.");
            }
        } else {
            System.out.println("User does not exist.");
        }
    }
}