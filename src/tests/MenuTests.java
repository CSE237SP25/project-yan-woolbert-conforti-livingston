package tests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import bankapp.BankCustomer;
import bankapp.Menu;

public class MenuTests {
	
	@Test
	public void testSignUpNewCustomer() {
		// 1. Set up objects being tested
		Menu menu = new Menu();
		String username = "TestUser";
		String password = "TestPassword1";
		
		//2. Call method under test
		menu.signUpCustomer(username, password);
        BankCustomer newCustomer = new BankCustomer(username);
        
        
        //3. Use assertions to verify results
        assertEquals(username, newCustomer.getUsername());			
	}
	
	@Test
	public void testMultipleSignUps() {
		//1. Set up objects being tested
		
		Menu menu = new Menu ();
		String username1 = "UserName1";
		String username2 = "UserName2";
		String password1 = "Password1";
		String password2 = "Password2";
		
		//2. Call method under test
		menu.signUpCustomer(username1, password1);
        menu.signUpCustomer(username2, password2);
        BankCustomer customer1 = new BankCustomer(username1);
        BankCustomer customer2 = new BankCustomer(username2);
        
        //3. Use assertions to verify results
        assertEquals(username1, customer1.getUsername());
        assertEquals(username2, customer2.getUsername());
	}
	

}
