package bankapp;

import java.util.Scanner;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class MainMenu {
    private BankRecord bankRecord;

    public MainMenu() {
        this.bankRecord = new BankRecord();
    }

    public void startMenu() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
        	System.out.println("\nFinancial Tidbit of the Day: " + generateFinancialTip());
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

	private String generateFinancialTip() {
    List<String> tips = Arrays.asList(
        "Create a budget. Then, actually use it!",
        "Avoid gambling! Gambling 1) can be addicting and 2) can lead to ruin.",
        "Save for your future! Retirement may be closer than it appears.",
        "Save for emergencies. Seems unnecessary until it isn't.",
        "Be aware that some of the money you earn will be taken from you by the government. But take heart: no taxation without representation, right?",
        "Keep track of your credit! Pay bills on time...",
        "Invest! Start with one of our high-yield savings accounts...",
        "Prepare your estate. You do not know what tomorrow will bring.",
        "Do not commit fraud...",
        "Want advice? Hire one of our financial advisors..."
    );
    return tips.get(new Random().nextInt(tips.size()));
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
        new CustomerMenu(scanner, customer, bankRecord).start(scanner, customer);
        } else {
            System.out.println("Invalid username or password.");
        }
    }
    public void signUpCustomer(String username, String password, BankRecord bankRecord) {
        BankCustomer newCustomer = new BankCustomer(username, password, bankRecord);
    }
}