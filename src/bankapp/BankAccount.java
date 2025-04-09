package bankapp;

import java.time.LocalDateTime;

public class BankAccount {
    // Declaration of private instance variables
    protected static int numberOfAccounts = 0;
    protected int accountID;
    protected double balance;
    protected double minimumBalance;
    protected BankRecord bankRecord;
    protected boolean isFrozen = false;
    
    // Default constructor for BankCustomer
    public BankAccount(BankRecord bankRecord) {
        this.accountID = numberOfAccounts++;
        this.balance = 0;
        this.minimumBalance = 0;
        this.bankRecord = bankRecord;
    }
    
    // Minimum balance constructor for BankCustomer
    public BankAccount(double minimumBalance, BankRecord bankRecord) {
        this.accountID = numberOfAccounts++;
        this.balance = 0;
        this.minimumBalance = minimumBalance;
        this.bankRecord = bankRecord;
    }
    
    public void freezeAccount() {
    	this.isFrozen = true;
    }
    
    public void unfreezeAccount() {
    	this.isFrozen = false;
    }
    
    public boolean isFrozen() {
        return isFrozen;
    }
    
    public void deposit(double amount) {
    	if (isFrozen) {
            throw new IllegalStateException("Account is frozen. No deposits allowed.");
        }
    	if(amount < 0) {
            throw new IllegalArgumentException();
        }
        this.balance += amount;
        
        Integer userID = bankRecord.getAccountIDUserID().get(this.accountID);
        if (userID != null) {
        	TransactionInfo transaction = new TransactionInfo("Deposit", amount, this.accountID);
        	bankRecord.recordTransaction(userID, transaction);
        }
    }
    
    public void withdraw(double amount) {
    	if (isFrozen) {
            throw new IllegalStateException("Account is frozen. No withdraws allowed.");
        }
    	if(amount < 0) {
            throw new IllegalArgumentException("Invalid amount: must be non-negative.");
        }
        if(this.balance - amount < minimumBalance) {
            double overdraftFee = 25.0;
            this.balance -= (amount + overdraftFee);
            
            Integer userID = bankRecord.getAccountIDUserID().get(this.accountID);
            if (userID != null) {
            	TransactionInfo transaction = new TransactionInfo("Withdrawal", amount, this.accountID);
                bankRecord.recordTransaction(userID, transaction);
            }
        }
        else if(this.balance >= amount) {
        	this.balance -= amount;
        	
        	Integer userID = bankRecord.getAccountIDUserID().get(this.accountID);
        	if (userID != null) {
        		TransactionInfo transaction = new TransactionInfo("Withdrawal", amount, this.accountID);
            	bankRecord.recordTransaction(userID, transaction);
        	}
        }
        else {
            throw new IllegalArgumentException("Insufficient funds.");
        }
    }
    
    // Getter and setter methods for BankAccount
    public void setMinimumBalance(double minimumBalance) {
        if(minimumBalance < 0 | minimumBalance > this.balance)
            throw new IllegalArgumentException();
        this.minimumBalance = minimumBalance;
    }
    
    public double getMinimumBalance() {
        return minimumBalance;
    }
    
    public double getCurrentBalance() {
        return this.balance;
    }
    
    public int getAccountID() {
        return this.accountID;
    }
}

