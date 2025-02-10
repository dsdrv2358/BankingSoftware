package edu.ithaca.dturnbull.bank;
public class ATM {

    protected Bank bank;
    private User authenticatedUser;

    public ATM(Bank bank) {
        this.bank = bank;
        this.authenticatedUser = null;
    }

    // Method to authenticate a user
    public boolean authenticate(String username, String password) {
        User user = bank.getUser(username);
        if (user != null && user.getPassword().equals(password)) {
            this.authenticatedUser = user;
            return true;
        }
        return false;
    }

    // Method to deposit money into an account
    public boolean deposit(String accountId, double amount) {
        if (authenticatedUser == null) {
            System.out.println("Please authenticate first.");
            return false;
        }
        Account account = authenticatedUser.getAccount(Long.parseLong(accountId));
        if (account != null) {
            if (account.isFrozen()) {
                System.out.println("Cannot deposit to a frozen account.");
                return false;
            }
            account.deposit(amount);
            return true;
        }
        System.out.println("Invalid account.");
        return false;
    }

    // Method to withdraw money from an account
    public boolean withdraw(String accountId, double amount) {
        if (authenticatedUser == null) {
            System.out.println("Please authenticate first.");
            return false;
        }
        Account account = authenticatedUser.getAccount(Long.parseLong(accountId));
        if (account != null) {
            if (account.isFrozen()) {
                System.out.println("Cannot withdraw from a frozen account.");
                return false;
            }
            if (account instanceof Savings) {
                Savings savingsAccount = (Savings) account;
                if (!savingsAccount.canWithdraw(amount)) {
                    System.out.println("Withdrawal limit exceeded for the day.");
                    return false;
                }
            }
            if (account.getBalance() >= amount) {
                account.withdraw(amount);
                return true;
            }
        }
        System.out.println("Insufficient funds or invalid account.");
        return false;
    }

    // Method to transfer money between accounts
    public boolean transfer(String fromAccountId, String toAccountId, double amount) {
        if (authenticatedUser == null) {
            System.out.println("Please authenticate first.");
            return false;
        }
        Account fromAccount = authenticatedUser.getAccount(Long.parseLong(fromAccountId));
        Account toAccount = bank.getAccount(Long.parseLong(toAccountId));
        if (fromAccount != null && toAccount != null) {
            if (fromAccount.isFrozen() || toAccount.isFrozen()) {
                System.out.println("Cannot transfer to or from a frozen account.");
                return false;
            }
            if (fromAccount.getBalance() >= amount) {
                fromAccount.withdraw(amount);
                toAccount.deposit(amount);
                return true;
            }
        }
        System.out.println("Insufficient funds or invalid accounts.");
        return false;
    }
}