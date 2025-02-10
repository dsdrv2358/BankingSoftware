package edu.ithaca.dturnbull.bank;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Admin {

    private Bank bank;

    public Admin(Bank bank) {
        this.bank = bank;
    }

    // Method to check the overall amount of money across all accounts
    public double getTotalMoneyInBank() {
        double total = 0.0;
        for (Account account : bank.getAccounts().values()) {
            total += account.getBalance();
        }
        return total;
    }

    // Method to request a report of accounts with suspicious activity
    public List<Account> getSuspiciousAccounts() {
        List<Account> suspiciousAccounts = new ArrayList<>();
        for (Account account : bank.getAccounts().values()) {
            if (isSuspicious(account)) {
                suspiciousAccounts.add(account);
            }
        }
        return suspiciousAccounts;
    }

    // Helper method to determine if an account has suspicious activity
    private boolean isSuspicious(Account account) {
        // Implement your logic to determine suspicious activity
        // For example, you can check for large transactions or frequent withdrawals
        return account.getBalance() > 1000000; // Example condition
    }

    // Method to freeze an account
    public void freezeAccount(long acctNum) {
        Account account = bank.getAccount(acctNum);
        if (account != null) {
            account.setFrozen(true);
        }
    }

    // Method to unfreeze an account
    public void unfreezeAccount(long acctNum) {
        Account account = bank.getAccount(acctNum);
        if (account != null) {
            account.setFrozen(false);
        }
    }
}