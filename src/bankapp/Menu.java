package bankapp;

import java.util.Scanner;

public class Menu {
	
	public void startMenu() {
		Scanner scanner = new Scanner (System.in);
		
		
		System.out.print("Enter your new username: ");
        String newUsername = scanner.nextLine();
        System.out.print("Enter your new password: ");
        String newPassword = scanner.nextLine();
        
        signUpCustomer(newUsername, newPassword);
	}
	
	public void signUpCustomer(String username, String password) {
		BankCustomer newCustomer = new BankCustomer(username);		
	}

}
