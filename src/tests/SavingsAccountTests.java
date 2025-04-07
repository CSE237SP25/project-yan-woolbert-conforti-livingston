package tests;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import bankapp.SavingsAccount;

public class SavingsAccountTests {

	@Test
	public void testInterestAccumulationSuccess() {
		SavingsAccount account = new SavingsAccount(0.01, LocalDate.now().minusDays(1));
		account.deposit(100.0);

		account.updateInterest();
		assertEquals(101.0, account.getCurrentBalance(), 0.0001);

		account.updateInterest();
		assertEquals(101.0, account.getCurrentBalance(), 0.0001);
	}

	private SavingsAccount account;

	@Test
	public void testInterestNotAppliedTwiceInSameDay() {
		SavingsAccount account = new SavingsAccount(0.01, LocalDate.now().minusDays(1));
		account.deposit(100.0);

		account.updateInterest();
		assertEquals(101.0, account.getCurrentBalance(), 0.0001);

		account.updateInterest();
		assertEquals(101.0, account.getCurrentBalance(), 0.0001);
	}

	@Test
	public void testNoInterestWithoutDeposit() {
		SavingsAccount account = new SavingsAccount(0.01, LocalDate.now());
		account.updateInterest();
		assertEquals(0.0, account.getCurrentBalance(), 0.0001);
	}
}
