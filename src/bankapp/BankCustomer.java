package bankapp;

public class BankCustomer {
	// Create variable corresponding to number of bank customer (will be used for user id)
	static int numberOfBankCustomers = 0;
	
	// Other private variable declarations
	private String username;
	// UserID will be unique across BankCustomers
	private int userID;
	
	// Default constructor for BankCustomer
	public BankCustomer(String username) {
		this.username = username;
		this.userID = numberOfBankCustomers;
		numberOfBankCustomers++;
	}
	
	// Getter and setter methods for BankCustomer
	public String getUsername() {
		return this.username;
	}
	
	public void setUsername(String u) {
		this.username = u;
	}
	
	public int getUserID() {
		return this.userID;
	}
}