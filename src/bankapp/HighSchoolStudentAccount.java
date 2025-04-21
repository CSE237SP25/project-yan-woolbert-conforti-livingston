package bankapp;

public class HighSchoolStudentAccount extends BankAccount {
	
	private static final double DEFAULT_MIN_BALANCE = 0.0;
    private static final double MAX_WITHDRAWAL = 100.0;
    
    public HighSchoolStudentAccount(BankRecord bankRecord) {
        super(DEFAULT_MIN_BALANCE, bankRecord);
    }
    
    @Override
    public void withdraw(double amount) {
        if (isFrozen()) {
            throw new IllegalStateException("Account is frozen. No withdrawals allowed.");
        }
        if (amount < 0) {
            throw new IllegalArgumentException("Invalid amount: must be non-negative.");
        }
        if (amount > MAX_WITHDRAWAL) {
            throw new IllegalArgumentException("Cannot withdraw more than $" + MAX_WITHDRAWAL + " at once in a high school account.");
        }
        if (getCurrentBalance() >= amount) {
            super.withdraw(amount);
        } else {
            throw new IllegalArgumentException("Insufficient funds.");
        }
    }
    
}
