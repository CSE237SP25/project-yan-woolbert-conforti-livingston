package tests;

import static org.junit.Assert.assertEquals;
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
		
		assertEquals(record.getUserIDAccounts().get(123), null);
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
		assertEquals(record.getUserIDAccounts().get(123), null);
		assertEquals(record.getUserIDCustomer().get(123), customer1);
		assertEquals(record.getUserIDAccounts().get(456), null);
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
		record.addAccount(123, account);
		
		//3. Use assertions to verify results		
		assertEquals(record.getUserIDAccounts().get(123).get(0), account);
		assertEquals(record.getUserIDCustomer().get(123), null);
	}
	
	@Test
	public void addMultipleAccounts() {
		//1. Create objects to be tested
		BankRecord record = new BankRecord();
		BankAccount account1 = new BankAccount();
		BankAccount account2 = new BankAccount();

		//2. Call methods to be tested
		record.addAccount(123, account1);
		record.addAccount(123, account2);
		
		//3. Use assertions to verify results		
		assertEquals(record.getUserIDAccounts().get(123).get(0), account1);
		assertEquals(record.getUserIDAccounts().get(123).get(1), account2);
		assertEquals(record.getUserIDCustomer().get(123), null);
	}
	
	@Test
	public void addMultipleAccountsWithSameID() {
		//1. Create objects to be tested
		BankRecord record = new BankRecord();
		BankAccount account1 = new BankAccount();
		BankAccount account2 = new BankAccount();

		//2. Call methods to be tested
		//3. Use assertions to verify results
		try {
			record.addAccount(123, account1);
			record.addAccount(123, account1);
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(e != null);
		}
		
		try {
			record.getUserIDAccounts().get(123).get(1);
			fail();
		} catch (IndexOutOfBoundsException e) {
			assertTrue(e != null);
		}
		
		assertEquals(record.getUserIDAccounts().get(123).get(0), account1);
		assertEquals(record.getUserIDCustomer().get(123), null);
	}


}
