package edu.ithaca.dturnbull.bank;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;


class BankAccountTest {

    private Bank bank;
    private ATM atm;
    private Teller teller;
    private User user;
    private Account account;

    @BeforeEach
    public void setUp() {
        bank = new Bank();
        atm = new ATM(bank);
        teller = new Teller(bank);
        user = new User("testuser", "password");
        account = new Account("testuser", "password");
        bank.addUser(user);
        user.addAccount(account);
        bank.addAccount(account);
    }

    @Test
    void getBalanceTest() {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);

        assertEquals(200, bankAccount.getBalance(), 0.001);
    }

    @Test
    void withdrawTest() throws InsufficientFundsException{
        BankAccount bankAccount = new BankAccount("a@b.com", 200);

        // normal
        bankAccount.withdraw(100);
        assertEquals(100, bankAccount.getBalance(), 0.001);

        // make sure 0 doesn't throw error
        bankAccount.withdraw(100);
        assertEquals(0, bankAccount.getBalance(), 0.001);

        // error testing
        assertThrows(InsufficientFundsException.class, () -> bankAccount.withdraw(300));
    }

    @Test
    void isEmailValidTest(){
        assertTrue(BankAccount.isEmailValid( "a@b.com"));   // valid email address
        assertFalse( BankAccount.isEmailValid(""));         // empty string

        
    }

    @Test
    void constructorTest() {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);

        assertEquals("a@b.com", bankAccount.getEmail());
        assertEquals(200, bankAccount.getBalance(), 0.001);
        //check for exception thrown correctly
        assertThrows(IllegalArgumentException.class, ()-> new BankAccount("", 100));
    }

    @Test
    public void testAuthenticate() {
        assertTrue(atm.authenticate("testuser", "password"));
        assertFalse(atm.authenticate("testuser", "wrongpassword"));
    }

    @Test
    public void testDeposit() {
        atm.authenticate("testuser", "password");
        assertTrue(atm.deposit(String.valueOf(account.getAcctNum()), 100.0));
        assertEquals(100.0, account.getBalance());
    }

    // @Test
    // public void testWithdraw() {
    //     atm.authenticate("testuser", "password");
    //     atm.deposit(String.valueOf(account.getAcctNum()), 100.0);
    //     assertTrue(atm.withdraw(String.valueOf(account.getAcctNum()), 50.0));
    //     assertEquals(50.0, account.getBalance());
    //     assertFalse(atm.withdraw(String.valueOf(account.getAcctNum()), 100.0)); // Insufficient funds
    // }

    @Test
    public void testTransfer() {
        atm.authenticate("testuser", "password");
        Account toAccount = new Account("testuser2", "password");
        bank.addAccount(toAccount);
        atm.deposit(String.valueOf(account.getAcctNum()), 100.0);
        assertTrue(atm.transfer(String.valueOf(account.getAcctNum()), String.valueOf(toAccount.getAcctNum()), 50.0));
        assertEquals(50.0, account.getBalance());
        assertEquals(50.0, toAccount.getBalance());
        assertFalse(atm.transfer(String.valueOf(account.getAcctNum()), String.valueOf(toAccount.getAcctNum()), 100.0)); // Insufficient funds
    }

    @Test
    public void testCreateUser() {
        teller.createUser("newuser", "newpassword");
        User newUser = bank.getUser("newuser");
        assertNotNull(newUser);
        assertEquals("newuser", newUser.getUsername());
    }

    @Test
    public void testDeleteUser() {
        teller.createUser("newuser", "newpassword");
        teller.deleteUser("newuser");
        User newUser = bank.getUser("newuser");
        assertNull(newUser);
    }

    @Test
        public void testCreateAccount() {
            teller.createAccount("testuser", "password", "savings");
            Account newAccount = null;
            for (Account acc : user.getAccounts().values()) {
                if (!acc.equals(account)) {
                    newAccount = acc;
                    break;
                }
            }
            assertNotNull(newAccount);
        }       

    @Test
    public void testDeleteAccount() {
        teller.createAccount("testuser", "password", "savings");
        Account newAccount = null;
        for (Account acc : user.getAccounts().values()) {
            if (!acc.equals(account)) {
                newAccount = acc;
                break;
            }
        }
        assertNotNull(newAccount);
        teller.deleteAccount("testuser", newAccount.getAcctNum());
        assertNull(user.getAccount(newAccount.getAcctNum()));
    }

    @Test
    public void testFreezeAndUnfreezeAccount() {
        Admin admin = new Admin(bank);
        admin.freezeAccount(account.getAcctNum());
        assertTrue(account.isFrozen());
        admin.unfreezeAccount(account.getAcctNum());
        assertFalse(account.isFrozen());
    }

    @Test
    public void testGetTotalMoneyInBank() {
        atm.authenticate("testuser", "password");
        atm.deposit(String.valueOf(account.getAcctNum()), 100.0);
        Admin admin = new Admin(bank);
        assertEquals(100.0, admin.getTotalMoneyInBank());
    }

    @Test
    public void testGetSuspiciousAccounts() {
        atm.authenticate("testuser", "password");
        atm.deposit(String.valueOf(account.getAcctNum()), 1000001.0); // Deposit more than 1,000,000 to mark as suspicious
        Admin admin = new Admin(bank);
        assertTrue(admin.getSuspiciousAccounts().contains(account));
    }
}