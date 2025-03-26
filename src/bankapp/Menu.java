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
            System.out.println("8. Log Out");
            System.out.print("Choose an option 1-8: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    openAccount(customer);
                    break;
                case "2":
                    closeAccount(scanner, customer);
                    break;
                case "3":
                    deposit(scanner);
                    break;
                case "4":
                    withdraw(scanner);
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
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private void openAccount(BankCustomer customer){
        int accountID = customer.addNewAccount(bankRecord);
        System.out.println("New account created with ID: " + accountID);
        return;
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

    private void deposit(Scanner scanner) {
        try{
            System.out.print("Enter account ID to deposit into: ");
            int depID = Integer.parseInt(scanner.nextLine());
            System.out.print("Enter amount to deposit: ");
            double depositAmt = Double.parseDouble(scanner.nextLine());
            BankAccount account = bankRecord.getAccountIDAccounts().get(depID);
            if (account == null) {
                System.out.println("Error: Account not found.");
                return;
            }
            account.deposit(depositAmt);
            System.out.println("Deposited $" + depositAmt + " to account " + depID);
        }catch (IllegalArgumentException e) {
            System.out.println("Error: Please enter a valid amount. Amount must be positive.");
        } catch (Exception e) {
            System.out.println("Error: Please enter a valid number.");
        }
    }

    private void withdraw(Scanner scanner) {
        try{
            System.out.print("Enter account ID to withdraw from: ");
            int wdID = Integer.parseInt(scanner.nextLine());
            System.out.print("Enter amount to withdraw: ");
            double withdrawAmt = Double.parseDouble(scanner.nextLine());
            BankAccount account = bankRecord.getAccountIDAccounts().get(wdID);
            if (account == null) {
                System.out.println("Error: Account not found.");
                return;
            }
            account.withdraw(withdrawAmt);
            System.out.println("Withdrew $" + withdrawAmt + " from account " + wdID);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: Insufficient funds or invalid amount.");
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
                System.out.println("Account ID: " + id + " | Balance: $" + acc.getCurrentBalance() +
                        " / Min: " + acc.getMinimumBalance());
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
