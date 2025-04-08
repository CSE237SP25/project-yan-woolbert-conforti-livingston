package tests;

import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.junit.Test;

import bankapp.BankAccount;
import bankapp.BankCustomer;
import bankapp.BankRecord;
import bankapp.Menu;
import bankapp.TransactionInfo;

public class TransactionInfoTests {
	@Test
	public void testTransactionInfoToString() {
		// 1. Set up objects being tested
		TransactionInfo testInfo = new TransactionInfo("Deposit", 100, 10);       
        
        //2. Use assertions to verify results
        assertEquals(testInfo.toString().compareTo(new String("Transaction [Deposit] on Account 10 for amount $100.0")), 0);			
	}
	
	@Test
    public void testDepositTransactionInfoRecording() {
		// 1. Set up objects being tested
        BankRecord bankRecord = new BankRecord();
        BankCustomer customer = new BankCustomer("Alex", "password", bankRecord);
        int userID = customer.getUserID();

        // Create a new checking account for the customer and deposit funds
        int accountID = customer.addNewCheckingAccount(bankRecord);
        BankAccount account = bankRecord.getAccountIDAccounts().get(accountID);
        account.deposit(50);

        //2. Use assertions to verify results
        ArrayList<TransactionInfo> history = bankRecord.getTransactionHistory(userID);
        assertEquals(1, history.size());
        TransactionInfo transaction = history.get(0);
        assertEquals("Deposit", transaction.getTransactionType());
        assertEquals(50.0, transaction.getAmount(), 0.001);
        assertEquals(accountID, transaction.getAccountID());
    }

    @Test
    public void testWithdrawalTransactionInfoRecording() {
    	// 1. Set up objects being tested
        BankRecord bankRecord = new BankRecord();
        BankCustomer customer = new BankCustomer("Alex", "password", bankRecord);
        int userID = customer.getUserID();

        // Create a new checking account and deposit and withdrawal funds
        int accountID = customer.addNewCheckingAccount(bankRecord);
        BankAccount account = bankRecord.getAccountIDAccounts().get(accountID);
        account.deposit(100.0);
        account.withdraw(40.0);

        //2. Use assertions to verify results
        ArrayList<TransactionInfo> history = bankRecord.getTransactionHistory(userID);
        assertEquals(2, history.size());
        TransactionInfo depositTransactionInfo = history.get(0);
        TransactionInfo withdrawalTransactionInfo = history.get(1);
        assertEquals("Deposit", depositTransactionInfo.getTransactionType());
        assertEquals(100.0, depositTransactionInfo.getAmount(), 0.001);
        assertEquals(accountID, depositTransactionInfo.getAccountID());
        assertEquals("Withdrawal", withdrawalTransactionInfo.getTransactionType());
        assertEquals(40.0, withdrawalTransactionInfo.getAmount(), 0.001);
        assertEquals(accountID, withdrawalTransactionInfo.getAccountID());
    }
    
    @Test
    public void testTransactionInfoRecordingMultipleAccounts() {
    	// 1. Set up objects being tested
        BankRecord bankRecord = new BankRecord();
        BankCustomer customer1 = new BankCustomer("Alex", "password", bankRecord);
        int userID1 = customer1.getUserID();
        BankCustomer customer2 = new BankCustomer("Steve", "password", bankRecord);
        int userID2 = customer2.getUserID();

        // Create a new checking account and deposit and withdrawal funds
        int accountID1 = customer1.addNewCheckingAccount(bankRecord);
        BankAccount account1 = bankRecord.getAccountIDAccounts().get(accountID1);
        account1.deposit(100.0);
        account1.withdraw(40.0);
        int accountID2 = customer2.addNewCheckingAccount(bankRecord);
        BankAccount account2 = bankRecord.getAccountIDAccounts().get(accountID2);
        account2.deposit(70.0);
        account2.withdraw(20.0);

        //2. Use assertions to verify results
        ArrayList<TransactionInfo> history = bankRecord.getTransactionHistory(userID1);
        assertEquals(2, history.size());
        TransactionInfo depositTransactionInfo = history.get(0);
        TransactionInfo withdrawalTransactionInfo = history.get(1);
        assertEquals("Deposit", depositTransactionInfo.getTransactionType());
        assertEquals(100.0, depositTransactionInfo.getAmount(), 0.001);
        assertEquals(accountID1, depositTransactionInfo.getAccountID());
        assertEquals("Withdrawal", withdrawalTransactionInfo.getTransactionType());
        assertEquals(40.0, withdrawalTransactionInfo.getAmount(), 0.001);
        assertEquals(accountID1, withdrawalTransactionInfo.getAccountID());
        
        ArrayList<TransactionInfo> history2 = bankRecord.getTransactionHistory(userID2);
        assertEquals(2, history2.size());
        TransactionInfo depositTransactionInfo2 = history2.get(0);
        TransactionInfo withdrawalTransactionInfo2 = history2.get(1);
        assertEquals("Deposit", depositTransactionInfo2.getTransactionType());
        assertEquals(70.0, depositTransactionInfo2.getAmount(), 0.001);
        assertEquals(accountID2, depositTransactionInfo2.getAccountID());
        assertEquals("Withdrawal", withdrawalTransactionInfo2.getTransactionType());
        assertEquals(20.0, withdrawalTransactionInfo2.getAmount(), 0.001);
        assertEquals(accountID2, withdrawalTransactionInfo2.getAccountID());
    }
}