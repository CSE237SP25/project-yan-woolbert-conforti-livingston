package bankapp;

import java.util.Scanner;

import java.util.ArrayList;
import java.util.Random;

public class CustomerMenu {
    private Scanner scanner;
    private BankCustomer customer;
    private BankRecord bankRecord;

    public CustomerMenu(Scanner scanner, BankCustomer customer, BankRecord bankRecord) {
        this.scanner = scanner;
        this.customer = customer;
        this.bankRecord = bankRecord;
    }
    public void start(Scanner scanner, BankCustomer customer) {
        while (true) {
            System.out.println("\n--- Customer Menu ---");
            System.out.println("1. Open an Account");
            System.out.println("2. Close an Account");
            System.out.println("3. Deposit");
            System.out.println("4. Withdraw");
            System.out.println("5. View Accounts");
            System.out.println("6. Transfer Funds");
            System.out.println("7. Set Minimum Balance");
            System.out.println("8. Merge Accounts");
            System.out.println("9. View Transaction History");
            System.out.println("10. Freeze Account");
            System.out.println("11. Unfreeze Account");
            System.out.println("12. Refer a Friend");
            System.out.println("13. Change your Password");
            System.out.println("14. Change your Username");
            System.out.println("15. Gamble Funds");
            System.out.println("16. Log Out");
            System.out.print("Choose an option 1-16: ");

            String choice = scanner.nextLine();
            if (handleCustomerChoice(choice, scanner, customer)) break;
        }
    }

    private boolean handleCustomerChoice(String choice, Scanner scanner, BankCustomer customer) {
        switch (choice) {
            case "1": openAccount(scanner, customer); break;
            case "2": closeAccount(scanner, customer); break;
            case "3": deposit(scanner, customer); break;
            case "4": withdraw(scanner, customer); break;
            case "5": viewAccounts(customer); break;
            case "6": transferFunds(scanner, customer); break;
            case "7": setMinimumBalance(scanner, customer); break;
            case "8": mergeAccounts(scanner, customer); break;
            case "9": viewTransactionHistory(scanner, customer); break;
            case "10": freezeAccount(scanner, customer); break;
            case "11": unfreezeAccount(scanner, customer); break;
            case "12": referFriend(scanner, customer); break;
            case "13": changePassword(scanner, customer); break;
            case "14": changeUsername(scanner, customer); break;
            case "15": gambleFunds(scanner, customer); break;
            case "16": System.out.println("Logging out..."); return true;
            default: System.out.println("Invalid choice.");
        }
        return false;
    }
    
    private void updateFundsBasedOnGamble(BankAccount account, double gambleAmt) {
    	// Calculate 0 or 1
        Random rand = new Random();
        int bit = rand.nextInt(2);
        System.out.println("Result: " + bit);
        if (bit == 1) {
        	System.out.println("You win!");
        	account.deposit(gambleAmt);
            System.out.println("Deposited $" + gambleAmt + " to account " + account.getAccountID());
        } else {
        	System.out.println("You lose!");
        	account.withdraw(gambleAmt);
            System.out.println("Withdrew $" + gambleAmt + " from account " + account.getAccountID());
        }
    }
    
    private void gambleFunds(Scanner scanner, BankCustomer customer) {
    	try {
    		System.out.println("You are about to play double-or-nothing (0 - you lose, 1 - you win).");
    		System.out.println("Please specify the account who's funds you want to gamble with: ");
        	int accountID = Integer.parseInt(scanner.nextLine());
        	validateAccountOwnership(customer, accountID);
        	BankAccount account = bankRecord.getAccountIDAccounts().get(accountID);
        	System.out.print("Enter amount to gamble: ");
            double gambleAmt = Double.parseDouble(scanner.nextLine());
            if (gambleAmt > account.getCurrentBalance()) {
            	System.out.println("Cannot gamble with more money than you have in your account!");
            	return;
            }
            updateFundsBasedOnGamble(account, gambleAmt);
	    } catch (Exception e) {
			System.out.println("Error: Invalid input.");
		}
    }
    
    private String getVerifiedNewValue(Scanner scanner, BankCustomer customer, String fieldType) {
        System.out.println("Please enter your current password:");
        String currentPassword = scanner.nextLine();

        if (!customer.isPasswordCorrect(currentPassword)) {
            System.out.println("Incorrect current password.");
            return null;
        }

        System.out.println("Please enter your new " + fieldType + ":");
        String newValue = scanner.nextLine();
        System.out.println("Please confirm your new " + fieldType + ":");
        String confirmNewValue = scanner.nextLine();

        if (!newValue.equals(confirmNewValue)) {
            System.out.println("New " + fieldType + " entries do not match. Please try again.");
            return null;
        }

        return newValue;
    }

    private void changeUsername(Scanner scanner, BankCustomer customer) {
        String newUsername = getVerifiedNewValue(scanner, customer, "username");
        if (newUsername == null) return;

        try {
            customer.setUsername(newUsername);
            System.out.println("Username successfully changed!");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void changePassword(Scanner scanner, BankCustomer customer) {
        String newPassword = getVerifiedNewValue(scanner, customer, "password");
        if (newPassword == null) return;

        customer.setPassword(newPassword);
        System.out.println("Password successfully changed!");
    }
    
    private void referFriend(Scanner scanner, BankCustomer customer) {
		System.out.println("Please enter the email address of your friend: ");
		String email = scanner.nextLine();
		if (!email.contains(".com") || !email.contains("@")) {
			System.out.println("Invalid email address. Returning to Menu...");
		}
		else {
			System.out.println("Your friend will now be contacted with marketing materials.");
			referralReward(scanner, customer);
		}
	}
    
    private void referralReward(Scanner scanner, BankCustomer customer) {
        try{
            System.out.print("Enter account ID to deposit into: ");
            int depID = Integer.parseInt(scanner.nextLine());
            BankAccount account = bankRecord.getAccountIDAccounts().get(depID);
            validateAccountOwnership(customer, depID);
            double depositAmt = 1.00;
            account.deposit(depositAmt);
            System.out.println("Deposited $" + depositAmt + " to account " + depID);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

	private void viewTransactionHistory(Scanner scanner, BankCustomer customer) {
    	try {
    		System.out.println("Would you like to view the history of all accounts (1) or view the history of a specific account? Choose 1-2:");
    		int choice = Integer.parseInt(scanner.nextLine());
    		if (choice != 1 && choice != 2) {
    			System.out.println("Invalid choice. Returning to Menu.");
    		}
    		else if (choice == 1) {
    			viewAllTransactionHistory(customer);
    		}
    		else {
    			viewOneAccountTransactionHistory(customer, scanner);
    		}
    	} catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
	
    private void viewAllTransactionHistory(BankCustomer customer){
        ArrayList<TransactionInfo> history = bankRecord.getTransactionHistory(customer.getUserID());
    	if (history != null) {
	   		for (TransactionInfo transactionInfo : history) {
	  			System.out.println(transactionInfo.toString());
	   		}
    	}
    }
    private void viewOneAccountTransactionHistory(BankCustomer customer, Scanner scanner){
        System.out.println("Please specify the account who's history you want to view: ");
    	int accountID = Integer.parseInt(scanner.nextLine());
    	validateAccountOwnership(customer, accountID);
    	ArrayList<TransactionInfo> history = bankRecord.getTransactionHistory(customer.getUserID());
    	if (history != null) {
	    	for (TransactionInfo transactionInfo : history) {
	    		if (transactionInfo.getAccountID() == accountID) {
	    			System.out.println(transactionInfo.toString());
	    		}
	    	}
    	}
    }
    private void mergeAccounts(Scanner scanner, BankCustomer customer) {
        try {
            System.out.print("Enter the first account ID: ");
            int acc1 = Integer.parseInt(scanner.nextLine());
            System.out.print("Enter the second account ID: ");
            int acc2 = Integer.parseInt(scanner.nextLine());
            customer.mergeAccounts(acc1, acc2);
            System.out.println("Accounts merged successfully(merged account ID: "+ acc1 + ").");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    private void openAccount(Scanner scanner, BankCustomer customer) {
        System.out.println("Would you like to open a checking account(1), savings account(2), or high-yield savings account(3)? Choose an option (1-3):");
        String accountType = scanner.nextLine();
        if (accountType.equals("1") || accountType.equals("2") || accountType.equals("3")) {
            int accountID;
            String accountTypeDescription;
            if (accountType.equals("1")) {
                accountID = customer.addNewCheckingAccount(bankRecord);
                accountTypeDescription = "checking";
            } else if (accountType.equals("2")) {
                accountID = customer.addNewSavingsAccount(bankRecord);
                accountTypeDescription = "savings (daily 0.0000027 interest rate)";
            } else {
                accountID = customer.addNewHighYieldSavingsAccount(bankRecord);
                accountTypeDescription = "high-yield savings(daily 0.0000054 interest rate, no withdrawal within 1 day of deposits.)";
            }
            System.out.println("New " + accountTypeDescription + " account created with ID: " + accountID);
        } else {
            System.out.println("Invalid choice. Returning to Menu.");
        }
    }
    
    private void freezeAccount(Scanner scanner, BankCustomer customer) {
    	try {
    		System.out.print("Enter the account ID to freeze: ");
    		int accountId = Integer.parseInt(scanner.nextLine());    		
    		BankAccount account = bankRecord.getAccountIDAccounts().get(accountId);
            validateAccountOwnership(customer, accountId);
    		account.freezeAccount();
    		System.out.println("Account " + accountId + " has been frozen.");
    	} catch (Exception e) {
    		System.out.println("Error: Invalid input.");
    	}
    }
    
    private void unfreezeAccount(Scanner scanner, BankCustomer customer) {
        try {
            System.out.print("Enter the account ID to unfreeze: ");
            int accountId = Integer.parseInt(scanner.nextLine());
            BankAccount account = bankRecord.getAccountIDAccounts().get(accountId);
            if (account == null || !customer.getUserAccounts(bankRecord).contains(accountId)) {
                System.out.println("Error: Account not found.");
                return;
            }
            account.unfreezeAccount();
            System.out.println("Account " + accountId + " has been unfrozen.");
        } catch (Exception e) {
            System.out.println("Error: Invalid input.");
        }
    }

    private void closeAccount(Scanner scanner, BankCustomer customer) {
        try{
            System.out.print("Enter account ID to close: ");
            int closeID = Integer.parseInt(scanner.nextLine());
            customer.removeAccount(bankRecord, closeID);
            System.out.println("Account " + closeID + " closed.");
        }catch(IllegalArgumentException e) {
            System.out.println("Error: Account does not exist.");
        }
    }

    private void deposit(Scanner scanner, BankCustomer customer) {
        try{
            System.out.print("Enter account ID to deposit into: ");
            int depID = Integer.parseInt(scanner.nextLine());
            BankAccount account = bankRecord.getAccountIDAccounts().get(depID);
            validateAccountOwnership(customer, depID);
            System.out.print("Enter amount to deposit: ");
            double depositAmt = Double.parseDouble(scanner.nextLine());
            account.deposit(depositAmt);
            System.out.println("Deposited $" + depositAmt + " to account " + depID);
        }catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void withdraw(Scanner scanner, BankCustomer customer) {
        try{
            System.out.print("Enter account ID to withdraw from: ");
            int wdID = Integer.parseInt(scanner.nextLine());
            BankAccount account = bankRecord.getAccountIDAccounts().get(wdID);
            validateAccountOwnership(customer, wdID);
            System.out.print("Enter amount to withdraw: ");
            double withdrawAmt = Double.parseDouble(scanner.nextLine());
            account.withdraw(withdrawAmt);
            System.out.println("Withdrew $" + withdrawAmt + " from account " + wdID);
        }catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void viewAccounts(BankCustomer customer) {
        ArrayList<Integer> accounts = customer.getUserAccounts(bankRecord);
        if (accounts == null || accounts.isEmpty()) {
            System.out.println("You have no accounts.");
        } else {
            for (int id : accounts) {
                BankAccount acc = bankRecord.getAccountIDAccounts().get(id);
                if (acc instanceof SavingsAccount) {
                    ((SavingsAccount) acc).updateInterest();
                }
                String type = acc instanceof CollegeStudentAccount ? "College Student Account"
                :acc instanceof HighSchoolStudentAccount ? "High School Student Account"
                :acc instanceof HighYieldSavingsAccount ? "High-Yield Savings"
                :acc instanceof SavingsAccount ? "Savings"
                : "Checking";
                String minBalanceDisplay = acc.hasMinimumBalanceSet()
                    ? "$" + acc.getMinimumBalance()
                    : "No minimum balance";
                System.out.println("Account ID: " + id + " | Type: " + type + " | Balance: $" + acc.getCurrentBalance() + " / Min: " + minBalanceDisplay);
            }
        }    
    }

    private void transferFunds(Scanner scanner, BankCustomer customer) {
        try {
            System.out.println("Enter account ID to transfer funds from: ");
            int fundsFromAccountID = Integer.parseInt(scanner.nextLine());
            validateAccountOwnership(customer, fundsFromAccountID);
            System.out.println("Enter account ID to transfer funds to: ");
            int fundsToAccountID = Integer.parseInt(scanner.nextLine());
            validateAccountOwnership(customer, fundsToAccountID);

            System.out.print("Enter amount to transfer: ");
            double transferAmount = Double.parseDouble(scanner.nextLine());
            customer.transferFundsBetweenAccount(fundsFromAccountID, fundsToAccountID, transferAmount);
            System.out.println("Transferred $" + transferAmount + " from account " + fundsFromAccountID + " to account " + fundsToAccountID);
        } catch (Exception e) {
            System.out.println("Error: Please enter a valid account ID and withdrawl amount in range.");
        }
        return;
    }

    private void setMinimumBalance(Scanner scanner, BankCustomer customer) {
        try {
            System.out.println("Enter account ID to set minimum balance of: ");
            int minimumAccountID = Integer.parseInt(scanner.nextLine());
            validateAccountOwnership(customer, minimumAccountID);
            System.out.print("Enter desired minimum balance: ");
            double minimumBalance = Double.parseDouble(scanner.nextLine());
            bankRecord.getAccountIDAccounts().get(minimumAccountID).setMinimumBalance(minimumBalance);
        } catch (Exception e) {
            System.out.println("Error: Please enter a valid account ID and minimum balance greater than or equal to your current balance.");
        }
    }


    private void validateAccountOwnership(BankCustomer customer, int id){
        BankAccount account = bankRecord.getAccountIDAccounts().get(id);
        if (account == null || !customer.getUserAccounts(bankRecord).contains(id)) {
            throw new IllegalArgumentException("Error: Account not found or not owned by you.");
        }
        if (account instanceof SavingsAccount) {
            ((SavingsAccount) account).updateInterest();
        }
    }
}