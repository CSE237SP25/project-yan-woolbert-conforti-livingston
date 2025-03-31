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

}
