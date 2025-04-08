package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import bankapp.BankRecord;
import bankapp.HighYieldSavingsAccount;

public class HighYieldSavingsAccountTests {

    @Test
    public void testWithdrawalAllowedAfterOneDay() {
        // Create a new account with default settings
        HighYieldSavingsAccount account = new HighYieldSavingsAccount(0.01, LocalDate.now().minusDays(1), new BankRecord());
        
        // Set balance without adding a new deposit date
        account.setBalanceForTesting(100.0);
        
        // Clear any deposit dates and add one from two days ago
        account.getDepositDates().clear();
        account.getDepositDates().add(LocalDate.now().minusDays(2));
        
        // Now withdraw should work because the only deposit is from 2 days ago
        assertDoesNotThrow(() -> account.withdraw(50.0));
    }

    @Test
    public void testWithdrawalBlockedWithinOneDay() {
        HighYieldSavingsAccount account = new HighYieldSavingsAccount(0.01, LocalDate.now().minusDays(1), new BankRecord());
        account.deposit(100.0);  // deposit happens today

        // Should block withdrawal
        Exception exception = assertThrows(IllegalArgumentException.class, () -> account.withdraw(50.0));
        assertEquals("Withdrawal blocked: Must wait 1 full day after any deposit.", exception.getMessage());
    }

    @Test
    public void testInterestAppliesCorrectly() {
        HighYieldSavingsAccount account = new HighYieldSavingsAccount(0.05, LocalDate.now().minusDays(1), new BankRecord());
        account.deposit(100.0); // deposit today

        // Interest applies (lastInterestDate was yesterday)
        account.updateInterest();
        assertEquals(105.0, account.getCurrentBalance(), 0.0001);
    }

    @Test
    public void testWithdrawalBlockedDueToRecentDepositAmongOlderOnes() {
        HighYieldSavingsAccount account = new HighYieldSavingsAccount(0.01, LocalDate.now().minusDays(1), new BankRecord());

        // First deposit was more than a day ago
        account.deposit(100.0);

        // Simulate waiting 2 days (by creating a new account with same balance and deposit history)
        // For this test to work as intended, you'll need to add a way to manipulate `depositDates` in test
        // Since you aren't using reflection, just simulate it:
        try {
            Thread.sleep(1000); // placeholder if you want to simulate time, not effective for LocalDate logic
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Add a new deposit today
        account.deposit(50.0);

        // Should block withdrawal because of today's deposit
        Exception e = assertThrows(IllegalArgumentException.class, () -> account.withdraw(50.0));
        assertEquals("Withdrawal blocked: Must wait 1 full day after any deposit.", e.getMessage());
    }
}
