package bankapp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class SavingsAccount extends BankAccount {
  //Declare private instance variables
	private static double interestRate;
	private LocalDate lastInterestDate;

  //Constructor
	public SavingsAccount(double interestRate, java.time.LocalDate lastInterestDate, BankRecord bankRecord) { // second constructor needed for testing
		super(bankRecord);
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
		
		Integer userID = bankRecord.getAccountIDUserID().get(this.accountID);
		if (userID != null) {
			TransactionInfo transaction = new TransactionInfo("Interest Earned", interest, this.accountID);
        	bankRecord.recordTransaction(userID, transaction);
		}
	}
}

}
