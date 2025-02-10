package edu.ithaca.dturnbull.bank;
import java.util.HashMap;
import java.util.Map;

public class Bank {
    private Map<Long, Account> accounts;
    private Map<String, User> users;

    // Create the bank with an empty list of accounts
    public Bank() {
        this.accounts = new HashMap<>();
        this.users = new HashMap<>();
    }

    public Map<Long, Account> getAccounts() {
        return accounts;
    }

    public User getUser(String username) {
        return users.get(username);
    }

    public void addUser(User user) {
        users.put(user.getUsername(), user);
    }

    public void removeUser(String username) {
        users.remove(username);
    }

    public void addAccount(Account account) {
        accounts.put(account.getAcctNum(), account);
    }

    public void removeAccount(long acctNum) {
        accounts.remove(acctNum);
    }

    public Account getAccount(long acctNum) {
        return accounts.get(acctNum);
    }
}