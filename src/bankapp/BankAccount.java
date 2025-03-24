package bankapp;

public class BankAccount {

	private double balance;
	private double minimumBalance;
	
	public BankAccount() {
		this.balance = 0;
		this.minimumBalance = 0;
	}
	
	public BankAccount(double minimumBalance) {
		this.balance = 0;
		this.minimumBalance = minimumBalance;
	}
	
	public void deposit(double amount) {
		if(amount < 0) {
			throw new IllegalArgumentException();
		}
		this.balance += amount;
	}
	
	public void withdraw(double amount) {
		if(amount < 0) {
			throw new IllegalArgumentException();
		}
		
		if(this.balance >= amount) {
		this.balance -= amount;
		}
		else {
			throw new IllegalArgumentException();
		}
	}
	public void setMinimumBalance(double minimumBalance) {
		if(minimumBalance < 0) 
			throw new IllegalArgumentException();
		this.minimumBalance = minimumBalance;
	}
	
	public double getMinimumBalance() {
		return minimumBalance;
	}
	
	public double getCurrentBalance() {
		return this.balance;
	}
}
