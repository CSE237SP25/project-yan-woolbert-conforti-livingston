package bankapp;

import java.time.LocalTime;

public class SavingsAccount extends BankAccount {
	private static double interestRate;
	private static LocalTime interestTime;

	public SavingsAccount(double interestRate, LocalTime interestTime) {
		super();
		SavingsAccount.interestRate = interestRate;
		SavingsAccount.interestTime = interestTime;
	}


	public void updateInterest() {
		LocalTime currentTime = LocalTime.of(LocalTime.now().getHour(),LocalTime.now().getMinute());
		double principal = super.getCurrentBalance();
		double cumulativeInterest = interestRate * principal;
		if (interestTime.equals(currentTime)) {
			this.balance += cumulativeInterest;
		}
	}

}
