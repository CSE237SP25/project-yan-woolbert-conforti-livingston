package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import bankapp.BankAccount;
import bankapp.BankCustomer;
import bankapp.BankRecord;

public class BankAccountTests {

	@Test
	public void testSimpleDeposit() {
		//1. Create objects to be tested
		BankAccount account = new BankAccount(new BankRecord());
		
		//2. Call the method being tested
		account.deposit(25);
		
		//3. Use assertions to verify results
		assertEquals(account.getCurrentBalance(), 25.0, 0.005);
	}
	
	@Test
	public void testNegativeDeposit() {
		//1. Create object to be tested
		BankAccount account = new BankAccount(new BankRecord());

		try {
			account.deposit(-25);
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(e != null);
		}
	}
	
	@Test
	public void testSimpleWithdrawal() {
		//1. Create objects to be tested
		BankAccount account = new BankAccount(new BankRecord());
				
		//2. Call the method being tested
		account.deposit(25);
		account.withdraw(5);
				
		//3. Use assertions to verify results
		assertEquals(account.getCurrentBalance(), 20.0, 0.005);
	}
	
	@Test
	public void testNegativeWithdrawal() {
		//1. Create object to be tested
		BankAccount account = new BankAccount(new BankRecord());

		try {
			account.withdraw(-25);
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(e != null);
		}
	
	}
	
	@Test
	public void testOverWithdrawal() {
		//1. Create objects to be tested
		BankAccount account = new BankAccount(new BankRecord());
		
		account.deposit(25);
		account.withdraw(50);
		
		//withdraw amount plus $25 fee
		assertEquals(-50, account.getCurrentBalance(), 0.005);
	}
	@Test
	public void testFreezePreventsWithdrawal() {
		BankAccount account = new BankAccount(new BankRecord());
		account.deposit(50);	
		account.freezeAccount();
		
		try {
			account.withdraw(25);
			fail();
		}catch (IllegalStateException e) {
			assertTrue(e != null);
		}	
	}
	@Test
	public void testFreezePreventsDeposit() {
		BankAccount account = new BankAccount(new BankRecord());
		account.freezeAccount();					
		try {
			account.deposit(50);
			fail();
		}catch (IllegalStateException e) {
			assertTrue(e != null);
		}	
	}
	
	
	@Test
	public void testUniqueAccountID() {
		BankRecord bankRecord = new BankRecord();
        BankCustomer customer = new BankCustomer("Alex", null, bankRecord);
        int userID = customer.getUserID();

        // Create multiple accounts
        int accountID1 = customer.addNewCheckingAccount(bankRecord);
        int accountID2 = customer.addNewCheckingAccount(bankRecord);
        int accountID3 = customer.addNewCheckingAccount(bankRecord);

        // Collect account IDs in a Set (ensures uniqueness)
        Set<Integer> accountIDs = new HashSet<>();
        accountIDs.add(accountID1);
        accountIDs.add(accountID2);
        accountIDs.add(accountID3);

        // Verify that all account IDs are unique
        assertEquals(3, accountIDs.size());

        // Ensure the created account IDs are correctly stored
        List<Integer> userAccounts = customer.getUserAccounts(bankRecord);
        assertTrue(userAccounts.contains(accountID1));
        assertTrue(userAccounts.contains(accountID2));
        assertTrue(userAccounts.contains(accountID3));
	}
	
	
	@Test
	public void testNegativeMinimumBalance() {
		BankAccount account = new BankAccount(100, new BankRecord());

		try {
			account.withdraw(-100);
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(e != null);
		}
	}
	@Test
	public void testMinimumBalanceNotLowerThanBalance() {
		BankAccount account = new BankAccount(100, new BankRecord());
		account.deposit(1);
		try {
			account.setMinimumBalance(10);;
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(e != null);
		}
	}
	
	@Test 
	public void testWithdrawBelowMinimumBalance(){
		//aka testing charging overdraft fee
		BankAccount account = new BankAccount(50, new BankRecord());
		account.deposit(100);
		
		account.withdraw(60);
		
		//100 - 60 - 25 (overdraft fee) = 15
		assertEquals(15.0, account.getCurrentBalance(), 0.005);
	}
	
	@Test 
	public void testWithdrawAboveMinimumBalance(){
		BankAccount account = new BankAccount(50, new BankRecord());
		account.deposit(100);
		
		account.withdraw(40);
		
		assertEquals(60.0, account.getCurrentBalance(), 0.005);
	}
	
	@Test 
	public void testWithdrawExactAmountToReachMinimumBalance(){
		BankAccount account = new BankAccount(50, new BankRecord());
		account.deposit(100);
		
		account.withdraw(50);
		
		assertEquals(50.0, account.getCurrentBalance(), 0.005);
	}
	
}
