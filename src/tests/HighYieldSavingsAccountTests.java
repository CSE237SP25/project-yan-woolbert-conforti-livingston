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
        HighYieldSavingsAccount account = new HighYieldSavingsAccount(0.01, LocalDate.now().minusDays(1), new BankRecord());
        
        account.deposit(100.0);
        //clear dates and set deposit date to 2 days ago, should allow withdrawal
        account.getDepositDates().clear();
        account.getDepositDates().add(LocalDate.now().minusDays(2));
        
        assertDoesNotThrow(() -> account.withdraw(50.0));
    }

    @Test
    public void testWithdrawalBlockedWithinOneDay() {
        HighYieldSavingsAccount account = new HighYieldSavingsAccount(0.01, LocalDate.now().minusDays(1), new BankRecord());
        account.deposit(100.0);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> account.withdraw(50.0));
        assertEquals("Withdrawal blocked: Must wait 1 full day after any deposit.", exception.getMessage());
    }

    @Test
    public void testInterestAppliesCorrectly() {
        HighYieldSavingsAccount account = new HighYieldSavingsAccount(0.05, LocalDate.now().minusDays(1), new BankRecord());
        account.deposit(100.0);

        account.updateInterest();
        assertEquals(105.0, account.getCurrentBalance(), 0.0001);
    }

    @Test
    public void testWithdrawalBlockedDueToRecentDepositAmongOlderOnes() {
        HighYieldSavingsAccount account = new HighYieldSavingsAccount(0.01, LocalDate.now().minusDays(1), new BankRecord());

        account.deposit(100.0);
        account.getDepositDates().clear();
        account.getDepositDates().add(LocalDate.now().minusDays(2));

        account.deposit(50.0);

        Exception e = assertThrows(IllegalArgumentException.class, () -> account.withdraw(50.0));
        assertEquals("Withdrawal blocked: Must wait 1 full day after any deposit.", e.getMessage());
    }
}
