package bankapp;

import java.time.LocalDateTime;

public class TransactionInfo {
	// Basic private instance variables
    private String transactionType;
    private double amount;
    private int accountID;
    
    // Transaction constructor (need to provide all relevant information)
    public TransactionInfo(String transactionType, double amount, int accountID) {
        this.transactionType = transactionType;
        this.amount = amount;
        this.accountID = accountID;
    }
    
    // Getters for TransactionInfo    
    public String getTransactionType() {
        return transactionType;
    }
    
    public double getAmount() {
        return amount;
    }
    
    public int getAccountID() {
        return accountID;
    }
    
    // Basic toString which will convert the TransactionInfo object into a string to be printed on the terminal
    public String toString() {
        return "Transaction [" + transactionType + "] on Account " + accountID + " for amount $" + amount;
    }
}
