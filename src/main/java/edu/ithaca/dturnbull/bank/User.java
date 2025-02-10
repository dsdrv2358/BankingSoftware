package edu.ithaca.dturnbull.bank;

import java.util.HashMap;
import java.util.Map;

public class User {

    private String username;
    private String password;
    private Map<Long, Account> accounts;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.accounts = new HashMap<>();
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Map<Long, Account> getAccounts() {
        return accounts;
    }

    public Account getAccount(long accountId) {
        return accounts.get(accountId);
    }

    public void addAccount(Account account) {
        accounts.put(account.getAcctNum(), account);
    }

    public void removeAccount(long acctNum) {
        accounts.remove(acctNum);
    }
}