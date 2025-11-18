package it.unibo.bank.impl;

import it.unibo.bank.api.AccountHolder;
import it.unibo.bank.api.BankAccount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static it.unibo.bank.impl.SimpleBankAccount.MANAGEMENT_FEE;
import static it.unibo.bank.impl.StrictBankAccount.TRANSACTION_FEE;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for the {@link StrictBankAccount} class.
 */
class TestStrictBankAccount {

    // Create a new AccountHolder and a StrictBankAccount for it each time tests are executed.
    private AccountHolder mRossi;
    private BankAccount bankAccount;

    /**
     * Prepare the tests.
     * PASS
     */
    @BeforeEach
    public void setUp() {
        this.mRossi = new AccountHolder("Mario", "Rossi", 1);
        this.bankAccount = new StrictBankAccount(mRossi, 0.0);

    }

    /**
     * Test the initial state of the StrictBankAccount.
     * PASS
     */
    @Test
    public void testInitialization() {
        assertEquals(0.0, bankAccount.getBalance());
        assertEquals(mRossi, bankAccount.getAccountHolder());

    }

    /**
     * Perform a deposit of 100€, compute the management fees, and check that the balance is correctly reduced.
     */
    @Test
    public void testManagementFees() {
        double testAmount = 100;
        int testTransactionCount = 1;
        bankAccount.deposit(mRossi.getUserID(), testAmount );
        bankAccount.chargeManagementFees(mRossi.getUserID());
        double testExpected = testAmount - (MANAGEMENT_FEE+(testTransactionCount * TRANSACTION_FEE));
        assertEquals(testExpected,bankAccount.getBalance());
    }

    /**
     * Test that withdrawing a negative amount causes a failure.
     */
    @Test
    public void testNegativeWithdraw() {
        double testInitialBalance = bankAccount.getBalance();
        double testAmount = -100;
        try{
            bankAccount.withdraw(mRossi.getUserID(), testAmount);
            fail ("Error: Negative value accepted");
        }catch(IllegalArgumentException exception){
            assertEquals(testInitialBalance,bankAccount.getBalance());
        }

    }

    /**
     * Test that withdrawing more money than it is in the account is not allowed.
     */
    @Test
    public void testWithdrawingTooMuch() {
        double testInitialBalance = bankAccount.getBalance();
        double testAmount = 100;
        try{
            bankAccount.withdraw(mRossi.getUserID(), testAmount);
            fail ("Error: Money not found");
        }catch(IllegalArgumentException exception){
            assertEquals(testInitialBalance,bankAccount.getBalance());
        }
    }
}
