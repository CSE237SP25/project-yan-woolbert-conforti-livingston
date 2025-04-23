package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import org.junit.Test;

import bankapp.BankRecord;
import bankapp.CollegeStudentAccount;

public class CollegeStudentAccountTests {

	@Test
    public void testCannotWithdrawOverMaxLimit() {
        CollegeStudentAccount account = new CollegeStudentAccount(new BankRecord());
        account.deposit(200.0);

        Exception e = assertThrows(IllegalArgumentException.class, () -> {
            account.withdraw(550.0); 
        });

        assertEquals("Cannot withdraw more than $500.0 at once in a college account.", e.getMessage());
    }
	
	@Test
    public void testCanWithdrawUpToMaxLimit() {
        
		CollegeStudentAccount account = new CollegeStudentAccount(new BankRecord());
        account.deposit(550.0);
        
        account.withdraw(500.0);

        assertEquals(50.0, account.getCurrentBalance(), 0.001);
    }
	
	@Test
    public void testFrozenAccountBlocksWithdrawal() {
        CollegeStudentAccount account = new CollegeStudentAccount(new BankRecord());
        account.deposit(100.0);
        account.freezeAccount();

        Exception e = assertThrows(IllegalStateException.class, () -> {
            account.withdraw(50.0);
        });

        assertEquals("Account is frozen. No withdrawals allowed.", e.getMessage());
    }
	
	@Test
    public void testCannotWithdrawMoreThanBalance() {
        CollegeStudentAccount account = new CollegeStudentAccount(new BankRecord());
        account.deposit(50.0);

        Exception e = assertThrows(IllegalArgumentException.class, () -> {
            account.withdraw(75.0);
        });

        assertEquals("Insufficient funds.", e.getMessage());
    }
	
	@Test
    public void testDepositAndWithdrawNormally() {
        CollegeStudentAccount account = new CollegeStudentAccount(new BankRecord());
        account.deposit(100.0);
        account.withdraw(60.0);

        assertEquals(40.0, account.getCurrentBalance(), 0.001);
    }
	
}
