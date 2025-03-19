package bankapp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BankRecord {

	// Private variable declarations
	private Map<Integer, ArrayList<Integer>> userIDAccountIDs;
	private Map<Integer, BankCustomer> userIDCustomer;
	private Map<Integer, ArrayList<BankAccount>> userIDAccounts;
	
	// Default constructor for BankRecord
	public BankRecord() {
		this.userIDAccountIDs = new HashMap<>();
		this.userIDCustomer = new HashMap<>();
		this.userIDAccounts = new HashMap<>();
	}
	
	//Getters for all hashMaps
	public Map<Integer, ArrayList<Integer>> getUserIDAccountIDs() {
		return userIDAccountIDs;
	}

	public Map<Integer, BankCustomer> getUserIDCustomer() {
		return userIDCustomer;
	}

	public Map<Integer, ArrayList<BankAccount>> getUserIDAccounts() {
		return userIDAccounts;
	}

	// Add user accounts, BankCustomer objects, and BankRecord objects to their respective HashMaps	
	public void addUser(int userID, BankCustomer customer) {
		if(getUserIDAccountIDs().containsKey(userID)) {
			throw new IllegalArgumentException();
		}
		else {
		userIDAccountIDs.put(userID, new ArrayList<Integer>());
		userIDCustomer.put(userID, customer);
		}
	}

	public void addAccount(int userID, BankAccount account) {
		if(getUserIDAccountIDs().containsKey(userID)) {
			if (getUserIDAccounts().get(userID).contains(account)) {
				throw new IllegalArgumentException();
			}
			else {
			getUserIDAccounts().get(userID).add(account);
			}
		}
		else {
		userIDAccountIDs.put(userID, new ArrayList<Integer>());
		userIDAccounts.put(userID, new ArrayList<BankAccount>());
		getUserIDAccounts().get(userID).add(account);
		}
	}

	
	
	

	
}
