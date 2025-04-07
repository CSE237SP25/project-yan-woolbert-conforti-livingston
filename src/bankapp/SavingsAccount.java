package bankapp;
import java.time.LocalDate;
import java.time.LocalTime;

public class SavingsAccount extends BankAccount {
  //Declare private instance variables
	private static double interestRate;
	private LocalDate lastInterestDate;

  //Constructor
	public SavingsAccount(double interestRate, java.time.LocalDate lastInterestDate) { // second constructor needed for testing
		super();
		SavingsAccount.interestRate = interestRate;
		this.lastInterestDate = lastInterestDate;
	}
  
  public void updateInterest() {
	LocalDate today = LocalDate.now();
	if (lastInterestDate.isBefore(today)) {
		double principal = this.balance;
		double interest = interestRate * principal;
		this.balance += interest;
		lastInterestDate = today;
	}
}

}
