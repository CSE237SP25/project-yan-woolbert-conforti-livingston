package bankapp;

public class BankAccount {

	private double balance;
	
	public BankAccount() {
		this.balance = 0;
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
	
	public double getCurrentBalance() {
		return this.balance;
	}
}
