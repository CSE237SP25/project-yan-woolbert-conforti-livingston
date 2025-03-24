package bankapp;

import java.util.Scanner;

public class Menu {
	
	public void startMenu() {
		Scanner scanner = new Scanner (System.in);
		BankRecord bankRecord = new BankRecord();
		
		
		System.out.print("Enter your new username: ");
        String newUsername = scanner.nextLine();
        System.out.print("Enter your new password: ");
        String newPassword = scanner.nextLine();
        
        signUpCustomer(newUsername, newPassword, bankRecord);
	}
	
	public void signUpCustomer(String username, String password, BankRecord bankRecord) {
		BankCustomer newCustomer = new BankCustomer(username, password, bankRecord);		
	}

}
