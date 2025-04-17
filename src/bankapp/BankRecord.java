package bankapp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class BankRecord {

	// Private variable declarations
	private Map<Integer, ArrayList<Integer>> userIDAccountIDs;
	private Map<Integer, Integer> accountIDUserID;
	private Map<Integer, BankCustomer> userIDCustomer;
	private Map<Integer, BankAccount> accountIDAccounts;
	private Map<Integer, ArrayList<TransactionInfo>> userIDTransactionHistory;
	private Set<String> registeredUsernames;

	
	// Default constructor for BankRecord
	public BankRecord() {
		this.userIDAccountIDs = new HashMap<>();
		this.accountIDUserID = new HashMap<>();
		this.userIDCustomer = new HashMap<>();
		this.accountIDAccounts = new HashMap<>();
		this.userIDTransactionHistory = new HashMap<>();
		this.registeredUsernames = new HashSet<>();
	}
	
	//Getters for all hashMaps
	public Map<Integer, ArrayList<Integer>> getUserIDAccountIDs() {
		return userIDAccountIDs;
	}
	
	public Map<Integer, Integer> getAccountIDUserID() {
		return accountIDUserID;
	}

	public Map<Integer, BankCustomer> getUserIDCustomer() {
		return userIDCustomer;
	}

	public Map<Integer, BankAccount> getAccountIDAccounts() {
		return accountIDAccounts;
	}

	// Add user accounts, BankCustomer objects, and BankRecord objects to their respective HashMaps	
	public void addUser(int userID, BankCustomer customer) {
		if(getUserIDAccountIDs().containsKey(userID)) {
			throw new IllegalArgumentException();
		}
		if (registeredUsernames.contains(customer.getUsername())) {
	        throw new IllegalArgumentException("Username already taken");
	    }
		else {
		userIDAccountIDs.put(userID, new ArrayList<Integer>());
		userIDCustomer.put(userID, customer);
    	registeredUsernames.add(customer.getUsername());
		}
	}
	public boolean isUsernameTaken(String newUsername) {
		if (registeredUsernames.contains(newUsername)) {
	       return true;
	    }
		return false;
	}
	public void addUsername(String u){
		if (registeredUsernames.contains(u)) {
			throw new IllegalArgumentException("Unable to add a username that is already in the list.");
		}
		else{
			registeredUsernames.add(u);
		}
	}
	public void removeUsername(String u){
		if (registeredUsernames.contains(u)) {
			registeredUsernames.remove(u);
		}
		else{
			throw new IllegalArgumentException("Unable to remove a username that is not already in the list.");
		}
	}
	public void addAccount(int userID, int accountID, BankAccount account) {
		if(getUserIDAccountIDs().containsKey(userID)) {
			if (getAccountIDAccounts().values().contains(account) || getAccountIDAccounts().containsKey(accountID)) {
				throw new IllegalArgumentException();
			}
			else {
			accountIDAccounts.put(accountID, account);
			accountIDUserID.put(accountID, userID);
			userIDAccountIDs.get(userID).add(accountID);
			}
		}
		else {
		userIDAccountIDs.put(userID, new ArrayList<Integer>());
		userIDAccountIDs.get(userID).add(accountID);
		accountIDUserID.put(accountID, userID);
		accountIDAccounts.put(accountID, account);
		}
		
	}

	public void deleteAccount(int userID, int accountID) {
	    if (!userIDCustomer.containsKey(userID)) {
	        throw new IllegalArgumentException("User does not exist");
	    }
	    if (!accountIDAccounts.containsKey(accountID)) {
	        throw new IllegalArgumentException("Account ID does not exist");
	    }
	    if (!userIDAccountIDs.get(userID).contains(accountID)) {
	        throw new IllegalArgumentException("This account does not belong to the user");
	    }

	    // Remove the account from the user's list
	    userIDAccountIDs.get(userID).remove(Integer.valueOf(accountID));

	    // Remove the account from BankRecord
	    accountIDAccounts.remove(accountID);
	}
	
	public BankCustomer getCustomerByUsername(String username) {
	    for (BankCustomer customer : userIDCustomer.values()) {
	        if (customer.getUsername().equals(username)) {
	            return customer;
	        }
	    }
	    return null; // Not found
	}
	
	// Methods relating to transaction recording
	public void recordTransaction(int userID, TransactionInfo transaction) {
        // Add array list if needed
        if (!userIDTransactionHistory.containsKey(userID)) {
            userIDTransactionHistory.put(userID, new ArrayList<TransactionInfo>());
        }
        userIDTransactionHistory.get(userID).add(transaction);
    }
    
    public ArrayList<TransactionInfo> getTransactionHistory(int userID) {
        return userIDTransactionHistory.get(userID);
    }
}