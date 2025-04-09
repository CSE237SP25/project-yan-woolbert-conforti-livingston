package bankapp;

import java.time.LocalDate;
import java.util.ArrayList;

public class HighYieldSavingsAccount extends SavingsAccount {
    private ArrayList<LocalDate> depositDates;

    public HighYieldSavingsAccount(double interestRate, LocalDate lastInterestDate, BankRecord bankRecord) {
        super(interestRate, lastInterestDate, bankRecord);
        this.depositDates = new ArrayList<>();
    }

    @Override
    public void deposit(double amount) {
        if (isFrozen) {
            throw new IllegalStateException("Account is frozen. No deposits allowed.");
        }
        super.deposit(amount);
        depositDates.add(LocalDate.now());
    }

    @Override
    public void withdraw(double amount) {
        if (isFrozen) {
            throw new IllegalStateException("Account is frozen. No withdrawals allowed.");
        }
        LocalDate today = LocalDate.now();
        for (LocalDate depositDate : depositDates) {
            if (!depositDate.isBefore(today.minusDays(1))) {
                throw new IllegalArgumentException("Withdrawal blocked: Must wait 1 full day after any deposit.");
            }
        }
        super.withdraw(amount);
    }
//for test
     public ArrayList<LocalDate> getDepositDates() {
        return depositDates;
    }
}
