package bankapp;

import java.util.Scanner;
import java.util.ArrayList;

public class Menu {
    private BankRecord bankRecord;

    public Menu() {
        this.bankRecord = new BankRecord();
    }

    public void startMenu() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n1. Sign Up");
            System.out.println("2. Log In");
            System.out.println("3. Exit");
            System.out.print("Choose an option 1-3: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    signUp(scanner);
                    break;
                case "2":
                    login(scanner);
                    break;
                case "3":
                    System.out.println("Goodbye!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }
    private void signUp(Scanner scanner) {
        try{
            System.out.print("Enter your new username: ");
            String newUsername = scanner.nextLine();
            System.out.print("Enter your new password: ");
            String newPassword = scanner.nextLine();

            signUpCustomer(newUsername, newPassword, bankRecord);
            System.out.println("Sign up successful. You can now log in.");
        } catch (IllegalArgumentException e){
            System.out.println("This username is already taken. Please choose a different username.");
        }
    }

    private void login(Scanner scanner) {
        System.out.print("Enter your username: ");
        String username = scanner.nextLine();
        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        BankCustomer customer = bankRecord.getCustomerByUsername(username);
        if (customer == null) {
            System.out.println("Invalid username or password.");
            return;
        }
        if (customer.isPasswordCorrect(password)) {
        System.out.println("Login successful! Welcome, " + customer.getUsername());
        customerMenu(scanner, customer);
        } else {
            System.out.println("Invalid username or password.");
        }
    }

    private void customerMenu(Scanner scanner, BankCustomer customer) {
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
            System.out.println("12. Log Out");
            System.out.print("Choose an option 1-12: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    openAccount(scanner, customer);
                    break;  
                case "2":
                    closeAccount(scanner, customer);
                    break;
                case "3":
                    deposit(scanner, customer);
                    break;
                case "4":
                    withdraw(scanner, customer);
                    break;
                case "5":
                    viewAccounts(customer);
                    break;
                case "6":
                    transferFunds(scanner, customer);
                    break;
                case "7":
                    setMinimumBalance(scanner);
                    break;
                case "8":
                    mergeAccounts(scanner, customer);
                    break;
                case "9":
                    viewTransactionHistory(scanner, customer);
                    break;
                case "10":
                    freezeAccount(scanner, customer);
                    break;
                case "11":
                    unfreezeAccount(scanner, customer);
                    break;
                case "12":
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
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
    			ArrayList<TransactionInfo> history = bankRecord.getTransactionHistory(customer.getUserID());
    			if (history != null) {
	    			for (TransactionInfo transactionInfo : history) {
	    				System.out.println(transactionInfo.toString());
	    			}
    			}
    		}
    		else {
    			System.out.println("Please specify the account who's history you want to view: ");
    			int accountID = Integer.parseInt(scanner.nextLine());
                BankAccount account = bankRecord.getAccountIDAccounts().get(accountID);
    			if (account == null || !customer.getUserAccounts(bankRecord).contains(accountID)) {
    				System.out.println("Error: Account not found or not owned by you.");
    	            return;
    	        }
    			ArrayList<TransactionInfo> history = bankRecord.getTransactionHistory(customer.getUserID());
    			if (history != null) {
	    			for (TransactionInfo transactionInfo : history) {
	    				if (transactionInfo.getAccountID() == accountID) {
	    					System.out.println(transactionInfo.toString());
	    				}
	    			}
    			}
    		}
    	} catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
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
        
        System.out.println(
                "Would you like to open a checking account(1), savings account(2), or high-yield savings account(3)? Choose 1-3:");
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
            return;
        } else {
            System.out.println("Invalid choice. Returning to Menu.");
        }

    }
    
    private void freezeAccount(Scanner scanner, BankCustomer customer) {
    	try {
    		System.out.print("Enter the account ID to freeze: ");
    		int accountId = Integer.parseInt(scanner.nextLine());    		
    		BankAccount account = bankRecord.getAccountIDAccounts().get(accountId);
    		if (account == null || !customer.getUserAccounts(bankRecord).contains(accountId)) {
    			System.out.println("Error: Account not found or not owned by you.");
    	        return;	
    		}    		
    		account.freezeAccount();
    		System.out.println("Account " + accountId + " has been frozen.");
    		
    	}catch (Exception e) {
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
            if (account == null || !customer.getUserAccounts(bankRecord).contains(depID)) {
                System.out.println("Error: Account not found or not owned by you.");
                return;
            }
            if (account instanceof SavingsAccount) {
                ((SavingsAccount) account).updateInterest();
            }
            System.out.print("Enter amount to deposit: ");
            double depositAmt = Double.parseDouble(scanner.nextLine());
            account.deposit(depositAmt);
            System.out.println("Deposited $" + depositAmt + " to account " + depID);
        }catch (IllegalArgumentException e) {
            System.out.println("Error: Please enter a valid amount. Amount must be positive.");
        } catch (Exception e) {
            System.out.println("Error: Please enter a valid number.");
        }
    }

    private void withdraw(Scanner scanner, BankCustomer customer) {
        try{
            System.out.print("Enter account ID to withdraw from: ");
            int wdID = Integer.parseInt(scanner.nextLine());
            BankAccount account = bankRecord.getAccountIDAccounts().get(wdID);
            if (account == null || !customer.getUserAccounts(bankRecord).contains(wdID)) {
                System.out.println("Error: Account not found or not owned by you.");
                return;
            }
            if (account instanceof SavingsAccount) {
                ((SavingsAccount) account).updateInterest();
            }
            System.out.print("Enter amount to withdraw: ");
            double withdrawAmt = Double.parseDouble(scanner.nextLine());
            account.withdraw(withdrawAmt);
            System.out.println("Withdrew $" + withdrawAmt + " from account " + wdID);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error: Please enter a valid number.");
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
    
                String type;
                if (acc instanceof HighYieldSavingsAccount) {
                    type = "High-Yield Savings";
                } else if (acc instanceof SavingsAccount) {
                    type = "Savings";
                } else {
                    type = "Checking";
                }
    
                System.out.println("Account ID: " + id +
                                   " | Type: " + type +
                                   " | Balance: $" + acc.getCurrentBalance() +
                                   " / Min: $" + acc.getMinimumBalance());
            }
        }    
    }

    private void transferFunds(Scanner scanner, BankCustomer customer) {
        try {
                        System.out.println("Enter account ID to transfer funds from: ");
                        int fundsFromAccountID = Integer.parseInt(scanner.nextLine());
                        if (bankRecord.getAccountIDAccounts().get(fundsFromAccountID) == null) {
                System.out.println("Error: Account not found.");
                return;
            }
                        System.out.println("Enter account ID to transfer funds to: ");
                        int fundsToAccountID = Integer.parseInt(scanner.nextLine());
                        if (bankRecord.getAccountIDAccounts().get(fundsToAccountID) == null) {
                System.out.println("Error: Account not found.");
                return;
            }
            System.out.print("Enter amount to transfer: ");
                        double transferAmount = Double.parseDouble(scanner.nextLine());
                        customer.transferFundsBetweenAccount(fundsFromAccountID, fundsToAccountID, transferAmount); 
        } catch (Exception e) {
                        System.out.println("Error: Please enter a valid account ID and withdrawl amount in range.");
        }
        return;
    }

    private void setMinimumBalance(Scanner scanner) {
        try {
                        System.out.println("Enter account ID to set minimum balance of: ");
                        int minimumAccountID = Integer.parseInt(scanner.nextLine());
                        if (bankRecord.getAccountIDAccounts().get(minimumAccountID) == null) {
                System.out.println("Error: Account not found.");
                return;
            }
            System.out.print("Enter desired minimum balance: ");
                        double minimumBalance = Double.parseDouble(scanner.nextLine());
                        bankRecord.getAccountIDAccounts().get(minimumAccountID).setMinimumBalance(minimumBalance);
        } catch (Exception e) {
            System.out.println("Error: Please enter a valid account ID and minimum amount > 0.");
        }
    }

    public void signUpCustomer(String username, String password, BankRecord bankRecord) {
        BankCustomer newCustomer = new BankCustomer(username, password, bankRecord);
    }
}

