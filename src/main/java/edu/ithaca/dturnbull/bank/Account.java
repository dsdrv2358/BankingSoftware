package edu.ithaca.dturnbull.bank;

import java.util.Random;

public class Account {

    private String password;
    private String username;
    private double balance;
    private long acctNum;
    private double[] transactions;
    private boolean isFrozen;

    public Account(String username, String password) {
        this.username = username;
        this.password = password;
        this.acctNum = generateAccountNumber();
        this.balance = 0.0;
        this.transactions = new double[0];
        this.isFrozen = false;
    }

    private long generateAccountNumber() {
        Random random = new Random();
        return (long) (random.nextDouble() * 99999999999999L);
    }

    public long getAcctNum() {
        return acctNum;
    }

    public double getBalance() {
        return balance;
    }

    public double[] getHist() {
        return transactions;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void deposit(double amount) {
        if (!isFrozen && amount > 0) {
            this.balance += amount;
        } else {
            System.out.println("Cannot deposit to a frozen account.");
        }
    }

    public void withdraw(double amount) {
        if (!isFrozen && amount > 0 && this.balance >= amount) {
            this.balance -= amount;
        } else {
            System.out.println("Cannot withdraw from a frozen account or insufficient balance.");
        }
    }

    public boolean isFrozen() {
        return isFrozen;
    }

    public void setFrozen(boolean isFrozen) {
        this.isFrozen = isFrozen;
    }
}