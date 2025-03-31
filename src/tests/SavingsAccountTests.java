package tests;

import static org.junit.Assert.assertEquals;

import java.time.LocalTime;

import org.junit.jupiter.api.Test;

import bankapp.SavingsAccount;

public class SavingsAccountTests {

	@Test
	public void testInterestAccumulationFailure() {
		// 1. Create objects to be tested
		SavingsAccount account1 = new SavingsAccount(0.01, LocalTime.of(9, 0));

		// 2. Call the method being tested
		account1.deposit(25);
		account1.updateInterest();

		// 3. Use assertions to verify results
		assertEquals(account1.getCurrentBalance(), 25, 0.005);
	}

	@Test
	public void testInterestAccumulationSuccess() {
		// 1. Create objects to be tested
		LocalTime interestTime = LocalTime.of(LocalTime.now().getHour(), LocalTime.now().getMinute());
		SavingsAccount account2 = new SavingsAccount(0.01, interestTime);

		// 2. Call the method being tested
		account2.deposit(25);
		account2.updateInterest();

		// 3. Use assertions to verify results
		assertEquals(account2.getCurrentBalance(), 25.25, 0.0005);
	}
}
