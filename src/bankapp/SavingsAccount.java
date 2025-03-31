package bankapp;
import java.time.LocalTime;

public class SavingsAccount extends BankAccount {
  //Declare private instance variables
	private static double interestRate;
	private static LocalTime interestTime;

  //Constructor
	public SavingsAccount(double interestRate, LocalTime interestTime) {
		super();
		SavingsAccount.interestRate = interestRate;
		SavingsAccount.interestTime = interestTime;
	}
  
  //Enable user to update interest
	public void updateInterest() {
		LocalTime currentTime = LocalTime.of(LocalTime.now().getHour(),LocalTime.now().getMinute());
		double principal = super.getCurrentBalance();
		double cumulativeInterest = interestRate * principal;
		if (interestTime.equals(currentTime)) {
			this.balance += cumulativeInterest;
		}
	}

}
