package edu.ithaca.dturnbull.bank;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Savings extends Account {

    private static final double dailyWithdrawalLimit = 2000.00;
    private double dailyWithdrawnAmount;
    private double interestRate =.02;
    private ScheduledExecutorService scheduler;

    public Savings(String username, String password, double interestRate) {
        super(username, password);
        this.dailyWithdrawnAmount = 0.0;
        this.interestRate = interestRate;
        startInterestCompounding();
    }

    // Method to compound interest daily
    public void compoundInterest() {
        double interest = getBalance() * (interestRate / 365);
        setBalance(getBalance() + interest);
    }

    // Method to start the scheduled task for compounding interest
    private void startInterestCompounding() {
        scheduler = Executors.newScheduledThreadPool(1);
        long initialDelay = calculateInitialDelay();
        long period = 24 * 60 * 60; // 24 hours in seconds
        scheduler.scheduleAtFixedRate(this::compoundInterest, initialDelay, period, TimeUnit.SECONDS);
    }

    // Method to calculate the initial delay until midnight
    private long calculateInitialDelay() {
        long currentTime = System.currentTimeMillis();
        long midnightTime = currentTime + TimeUnit.DAYS.toMillis(1) - (currentTime % TimeUnit.DAYS.toMillis(1));
        return (midnightTime - currentTime) / 1000; // Convert milliseconds to seconds
    }

    // Method to stop the scheduled task
    public void stopInterestCompounding() {
        if (scheduler != null && !scheduler.isShutdown()) {
            scheduler.shutdown();
        }
    }

    public boolean canWithdraw(double amount) {
        return (dailyWithdrawnAmount + amount) <= dailyWithdrawalLimit;
    }
    // Method to reset daily withdrawn amount (should be called at midnight)
    public void resetDailyWithdrawnAmount() {
        dailyWithdrawnAmount = 0.0;
    }

}