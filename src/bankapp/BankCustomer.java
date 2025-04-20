package bankapp;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class BankCustomer {
    // Create variable corresponding to number of bank customer (will be used for user id)
    static int numberOfBankCustomers = 0;
    
    // Other private variable declarations
    private String username;
    private String password;
    // UserID will be unique across BankCustomers
    private int userID;
    // Other private instance variables
    private BankRecord bankRecord;
    
    // Default constructor for BankCustomer
    public BankCustomer(String username, String password, BankRecord bankRecord) {
        this.username = username;
        this.password = password;
        this.userID = numberOfBankCustomers++;
        this.bankRecord = bankRecord;
        bankRecord.addUser(userID, this);
    }
    
    public boolean isPasswordCorrect(String enteredPassword) {
        if (this.password.equals(enteredPassword)) {
            return true;
        }
        else {
            return false;
        }
    }
    
    public int addNewCheckingAccount(BankRecord bankRecord) {
        BankAccount newAccount = new BankAccount(bankRecord);
        bankRecord.addAccount(this.userID, newAccount.getAccountID(), newAccount);
        return newAccount.getAccountID();
    }
    
    public int addNewSavingsAccount(BankRecord bankRecord) {
        SavingsAccount newAccount = new SavingsAccount(0.0000027, LocalDate.now(), bankRecord);
        bankRecord.addAccount(this.userID, newAccount.getAccountID(), newAccount);
        return newAccount.getAccountID();
    }
    public int addNewHighYieldSavingsAccount(BankRecord bankRecord) {
        HighYieldSavingsAccount newAccount = new HighYieldSavingsAccount(0.0000054, LocalDate.now(), bankRecord);
        bankRecord.addAccount(this.userID, newAccount.getAccountID(), newAccount);
        return newAccount.getAccountID();
    }
    
    public int addNewHighSchoolAccount(BankRecord bankRecord) {
        HighSchoolStudentAccount newAccount = new HighSchoolStudentAccount(bankRecord);
        bankRecord.addAccount(this.userID, newAccount.getAccountID(), newAccount);
        return newAccount.getAccountID();
    }
    
    
    public void removeAccount(BankRecord bankRecord, int accountID) {
        bankRecord.deleteAccount(this.userID, accountID);
    }
    
    public void transferFundsBetweenAccount(int fundLosingAccountID, int fundGainingAccountID, double amountToTransfer) {
        if (!this.bankRecord.getUserIDAccountIDs().get(this.userID).contains(fundLosingAccountID)) {
            throw new IllegalArgumentException("Fund losing account is not owned by user");
        }
        if (!this.bankRecord.getUserIDAccountIDs().get(this.userID).contains(fundGainingAccountID)) {
            throw new IllegalArgumentException("Fund gaining account is not owned by user");
        }
        BankAccount fundLosingAccount = this.bankRecord.getAccountIDAccounts().get(fundLosingAccountID);
        BankAccount fundGainingAccount = this.bankRecord.getAccountIDAccounts().get(fundGainingAccountID);
        
        // Should not have to double check if it is possible because the logic will be covered by withdraw and deposit
        fundLosingAccount.withdraw(amountToTransfer);
        fundGainingAccount.deposit(amountToTransfer);
    }
    public void mergeAccounts(int accountID1, int accountID2) {
        if (accountID1 == accountID2) {
            throw new IllegalArgumentException("Cannot merge the same account.");
        }
    
        BankAccount acc1 = bankRecord.getAccountIDAccounts().get(accountID1);
        BankAccount acc2 = bankRecord.getAccountIDAccounts().get(accountID2);
    
        if (acc1 == null || acc2 == null) {
            throw new IllegalArgumentException("One or both accounts not found.");
        }
    
        if (!bankRecord.getUserIDAccountIDs().get(userID).contains(accountID1) ||
            !bankRecord.getUserIDAccountIDs().get(userID).contains(accountID2)) {
            throw new IllegalArgumentException("You must own both accounts to merge.");
        }
    
        if (!acc1.getClass().equals(acc2.getClass())) {
            throw new IllegalArgumentException("Can only merge accounts of the same type.");
        }
    
        // Transfer balance
        acc1.deposit(acc2.getCurrentBalance());
    
        // Remove second account
        removeAccount(bankRecord, accountID2);
    }
    
    // Getter and setter methods for BankCustomer
    public String getUsername() {
        return this.username;
    }
        
    public void setUsername(String u) {
        if(this.username.equals(u)){
            throw new IllegalArgumentException("You cannot change your username to your current username.");
        }
        if(this.bankRecord.isUsernameTaken(u)){
            throw new IllegalArgumentException("This username is already taken. Please choose another username.");
        }
        else{
            this.bankRecord.removeUsername(this.username);
            this.username = u;
            this.bankRecord.addUsername(u);
        }
    }
    
    public int getUserID() {
        return this.userID;
    }
    
    public void setPassword(String newPassword){
        this.password = newPassword;
    }

    public  ArrayList<Integer> getUserAccounts(BankRecord bankRecord) {
        ArrayList<Integer> accountIDs = bankRecord.getUserIDAccountIDs().get(this.userID);
        return accountIDs;
    }
}
