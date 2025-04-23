package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import org.junit.Test;

import bankapp.BankRecord;
import bankapp.HighSchoolStudentAccount;

public class HighSchoolStudentAccountTests {

	@Test
    public void testCannotWithdrawOverMaxLimit() {
        HighSchoolStudentAccount account = new HighSchoolStudentAccount(new BankRecord());
        account.deposit(200.0);

        Exception e = assertThrows(IllegalArgumentException.class, () -> {
            account.withdraw(150.0); 
        });

        assertEquals("Cannot withdraw more than $100.0 at once in a high school account.", e.getMessage());
    }
	
	@Test
    public void testCanWithdrawUpToMaxLimit() {
        
		HighSchoolStudentAccount account = new HighSchoolStudentAccount(new BankRecord());
        account.deposit(150.0);
        
        account.withdraw(100.0);

        assertEquals(50.0, account.getCurrentBalance(), 0.001);
    }
	
	@Test
    public void testFrozenAccountBlocksWithdrawal() {
        HighSchoolStudentAccount account = new HighSchoolStudentAccount(new BankRecord());
        account.deposit(100.0);
        account.freezeAccount();

        Exception e = assertThrows(IllegalStateException.class, () -> {
            account.withdraw(50.0);
        });

        assertEquals("Account is frozen. No withdrawals allowed.", e.getMessage());
    }
	
	@Test
    public void testCannotWithdrawMoreThanBalance() {
        HighSchoolStudentAccount account = new HighSchoolStudentAccount(new BankRecord());
        account.deposit(50.0);

        Exception e = assertThrows(IllegalArgumentException.class, () -> {
            account.withdraw(75.0);
        });

        assertEquals("Insufficient funds.", e.getMessage());
    }
	
	@Test
    public void testDepositAndWithdrawNormally() {
        HighSchoolStudentAccount account = new HighSchoolStudentAccount(new BankRecord());
        account.deposit(100.0);
        account.withdraw(60.0);

        assertEquals(40.0, account.getCurrentBalance(), 0.001);
    }
	
}
