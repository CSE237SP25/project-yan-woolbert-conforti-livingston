package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.jupiter.api.Test;

import bankapp.BankCustomer;
import bankapp.BankRecord;

public class BankCustomerTests {

	@Test
	public void testSimpleNewCustomer() {
		//1. Create objects to be tested
		BankCustomer customer = new BankCustomer("Test Customer");
		
		//2. Use assertions to verify results both username and user ID
		assertEquals(customer.getUsername(), "Test Customer");
		assertEquals(customer.getUserID(), 0);
	}
	
	@Test
	public void testMultipleNewCustomers() {
		
		//1. Create objects to be tested
		BankCustomer customer0 = new BankCustomer("Test Customer 0");
		BankCustomer customer1 = new BankCustomer("Test Customer 1");

		//2. Use assertions to verify results both username and user ID
		assertEquals(customer0.getUsername(), "Test Customer 0");
		assertEquals(customer1.getUsername(), "Test Customer 1");
		assertEquals(customer0.getUserID(), 3);
		assertEquals(customer1.getUserID(), 4);
	}
	
	@Test
	public void testSetUsername() {
		//1. Create objects to be tested
		BankCustomer customer = new BankCustomer("Test Customer");
		
		//2. Call methods to be tested
		customer.setUsername("New Username");

		//2. Use assertions to verify results both username and user ID
		assertEquals(customer.getUsername(), "New Username");
	}
	@Test
	public void testCreateNewAccount() {
		BankRecord bankRecord = new BankRecord();
        BankCustomer customer2 = new BankCustomer("Alice");
        int userID = customer2.getUserID();
        bankRecord.addUser(userID, customer2);
        
        int accountID = customer2.addNewAccount(bankRecord);
        List<Integer> userAccounts = customer2.getUserAccounts(bankRecord);

        assertTrue(userAccounts.contains(accountID));
        assertNotNull(bankRecord.getAccountIDAccounts().get(accountID));
	}
}
