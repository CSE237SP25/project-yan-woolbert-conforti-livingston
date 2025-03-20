package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import bankapp.BankAccount;
import bankapp.BankCustomer;
import bankapp.BankRecord;

public class BankRecordTests {

	@Test
	public void addOneUser() {
		//1. Create objects to be tested
		BankRecord record = new BankRecord();
		BankCustomer customer = new BankCustomer("Test");

		//2. Call methods to be tested
		record.addUser(123, customer);
		
		//3. Use assertions to verify results
		
		assertEquals(record.getUserIDCustomer().get(123), customer);
	}
	
	
	@Test
	public void addMultipleUsers() {
		//1. Create objects to be tested
		BankRecord record = new BankRecord();
		BankCustomer customer1 = new BankCustomer("Test1");
		BankCustomer customer2 = new BankCustomer("Test3");
		
		//2. Call methods to be tested
		record.addUser(123, customer1);
		record.addUser(456, customer2);

		//3. Use assertions to verify results
		assertEquals(record.getUserIDCustomer().get(123), customer1);
		assertEquals(record.getUserIDCustomer().get(456), customer2);
	}
	
	@Test
	public void addUsersWithSameID() {
		//1. Create objects to be tested
		BankRecord record = new BankRecord();
		BankCustomer customer1 = new BankCustomer("Test1");
		BankCustomer customer2 = new BankCustomer("Test3");
		
		//2. Call methods to be tested
		//3. Use assertions to verify results
		try {
			record.addUser(123, customer1);
			record.addUser(123, customer2);
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(e != null);
		}
		assertEquals(record.getUserIDCustomer().get(123), customer1);
	}
	
	@Test
	public void addOneAccount() {
		//1. Create objects to be tested
		BankRecord record = new BankRecord();
		BankAccount account = new BankAccount();

		//2. Call methods to be tested
		record.addAccount(123, 1, account);
		
		//3. Use assertions to verify results		
		assertTrue(record.getAccountIDAccounts().containsValue(account));
		assertTrue(record.getUserIDAccountIDs().get(123).get(0)==1);
	}
	
	@Test
	public void addMultipleAccounts() {
		//1. Create objects to be tested
		BankRecord record = new BankRecord();
		BankAccount account1 = new BankAccount();
		BankAccount account2 = new BankAccount();

		//2. Call methods to be tested
		record.addAccount(123, 1, account1);
		record.addAccount(123, 2, account2);
		
		//3. Use assertions to verify results	
		assertTrue(record.getAccountIDAccounts().containsValue(account1));
		assertTrue(record.getAccountIDAccounts().containsValue(account2));
		assertTrue(record.getUserIDAccountIDs().get(123).get(0)==1);
		assertTrue(record.getUserIDAccountIDs().get(123).get(1)==2);
	}
	
	@Test
	public void addMultipleAccountsWithSameObject() {
		//1. Create objects to be tested
		BankRecord record = new BankRecord();
		BankAccount account1 = new BankAccount();

		//2. Call methods to be tested
		//3. Use assertions to verify results
		try {
			record.addAccount(123, 1, account1);
			record.addAccount(123, 2, account1);
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(e != null);
		}
		
		assertEquals(record.getAccountIDAccounts().get(2), null);
		assertEquals(record.getAccountIDAccounts().get(1), account1);
	}


	@Test
	public void addMultipleAccountsWithSameID() {
		// 1. Create objects to be tested
		BankRecord record = new BankRecord();
		BankAccount account1 = new BankAccount();
		BankAccount account2 = new BankAccount();

		// 2. Call methods to be tested
		// 3. Use assertions to verify results
		try {
			record.addAccount(123, 1, account1);
			record.addAccount(123, 1, account2);
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(e != null);
		}

		assertEquals(record.getAccountIDAccounts().get(2), null);
		assertEquals(record.getAccountIDAccounts().get(1), account1);
	}
	@Test
    void testDeleteAccount() {
		BankRecord bankRecord = new BankRecord();
        BankCustomer customer = new BankCustomer("Alice");
        bankRecord.addUser(customer.getUserID(), customer);
        int userID = customer.getUserID();
        int accountID = customer.addNewAccount(bankRecord);
        BankAccount account = bankRecord.getAccountIDAccounts().get(accountID);

        // Verify account exists before deletion
        assertTrue(bankRecord.getUserIDAccountIDs().get(userID).contains(account.getAccountID()));

        // Delete the account
        bankRecord.deleteAccount(userID, account.getAccountID());

        // Verify account is removed
        assertFalse(bankRecord.getUserIDAccountIDs().get(userID).contains(account.getAccountID()));
        assertNull(bankRecord.getAccountIDAccounts().get(account.getAccountID()));
    }

    @Test
    void testDeleteNonExistentAccount() {
    	BankRecord bankRecord = new BankRecord();
        BankCustomer customer = new BankCustomer("Alice");
        bankRecord.addUser(customer.getUserID(), customer);
        int userID = customer.getUserID();

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            bankRecord.deleteAccount(userID, 9999);
        });

        assertEquals("Account ID does not exist", exception.getMessage());
    }

    @Test
    void testDeleteAccountNotOwnedByUser() {
    	BankRecord bankRecord = new BankRecord();
        BankCustomer customer = new BankCustomer("Alice");
        bankRecord.addUser(customer.getUserID(), customer);
        BankCustomer anotherCustomer = new BankCustomer("Bob");
        bankRecord.addUser(anotherCustomer.getUserID(), anotherCustomer);
        int userID = customer.getUserID();
        int accountID = customer.addNewAccount(bankRecord);
        BankAccount account = bankRecord.getAccountIDAccounts().get(accountID);

        // Try deleting Alice's account as Bob
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            bankRecord.deleteAccount(anotherCustomer.getUserID(), account.getAccountID());
        });

        assertEquals("This account does not belong to the user", exception.getMessage());
    }
	
}
