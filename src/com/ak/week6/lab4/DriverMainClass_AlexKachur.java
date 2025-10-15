package com.ak.week6.lab4;

import java.util.Locale;
import java.util.Scanner;

/*
 * File: DriverMainClass_AlexKachur.java
 * @author Alex Kachur
 * @since 2025-10-9
 * @purpose Console menu to create and manage Bank and Savings accounts polymorphically.
 */

/**
 * Menu-driven console driver for Lab 3. Demonstrates abstract classes,
 * inheritance, polymorphism, and method overloading.
 */
public class DriverMainClass_AlexKachur {

	private static final int MAX_ACCOUNTS = 100;
	private static final BankAccount_AK[] ACCOUNTS = new BankAccount_AK[MAX_ACCOUNTS];
	private static int count = 0; 

	public static void main(String[] args) {
		Locale.setDefault(Locale.US);
		Scanner in = new Scanner(System.in); // Using the Scanner library as required

		System.out.println("=== Lab3 Week6 — Accounts Menu (AK) ===");

		boolean running = true;
		while (running) {
			System.out.println("\nMenu:");
			System.out.println("1) Create account");
			System.out.println("2) Deposit");
			System.out.println("3) Deposit with Interest (Overloaded Method)");
			System.out.println("4) Withdraw");
			System.out.println("5) List accounts");
			System.out.println("0) Exit");
			int choice = readInt(in, "Choose: ", 0, 5);
			switch (choice) {
			case 1 -> createAccountFlow(in);
			case 2 -> depositFlow(in);
			case 3 -> depositWithInterestFlow(in);
			case 4 -> withdrawFlow(in);
			case 5 -> listAccounts();
			case 0 -> running = false;
			}
		}

		in.close();
		System.out.println("\nGoodbye!");
	}

	// --- Flows ---

	private static void createAccountFlow(Scanner in) {
		if (count >= MAX_ACCOUNTS) {
			System.out.println("Sorry, storage is full.");
			return;
		}

		System.out.println("\nCreate which type?");
		System.out.println("1) Bank Account");
		System.out.println("2) Savings Account ($50 minimum balance on withdraw)");
		int type = readInt(in, "Choose: ", 1, 2);

		// Get dynamic inputs as required
		String firstName = readLine(in, "Enter first name: ");
		String lastName = readLine(in, "Enter last name: ");
		String accNo = readLine(in, "Enter account number: ");
		double init = readDouble(in, "Enter initial balance: ");

		try {
			BankAccount_AK acc;
			if (type == 1) {
				// We cannot do 'new BankAccount_AK(...)', because it's abstract.
				// We create a concrete anonymous inner class that extends it.
				acc = new BankAccount_AK(firstName, lastName, accNo, init) {}; 
			} else {
				acc = new SavingsAccount_AK(firstName, lastName, accNo, init);
			}

			ACCOUNTS[count++] = acc; // Store the object polymorphically
			System.out.printf("Successfully created account:%n%s%n", acc.toString());
		} catch (RuntimeException ex) {
			System.out.println("Creation failed: " + ex.getMessage());
		}
	}

	private static void depositFlow(Scanner in) {
		BankAccount_AK acc = chooseAccount(in);
		if (acc == null) return;

		System.out.printf("Selected Account: %s%n", acc.toString());
		double amount = readDouble(in, "Enter deposit amount: ");
		try {
			acc.deposit(amount);
			System.out.printf("Deposit successful. New balance: $%.2f%n", acc.getBalance());
		} catch (RuntimeException ex) {
			System.out.println("Deposit failed: " + ex.getMessage());
		}
	}
	
	private static void depositWithInterestFlow(Scanner in) {
		BankAccount_AK acc = chooseAccount(in);
		if (acc == null) return;

		System.out.printf("Selected Account: %s%n", acc.toString());
		double amount = readDouble(in, "Enter deposit amount: ");
		double interest = readDouble(in, "Enter interest rate (e.g., 0.05 for 5%): ");
		try {
			// Calling the overloaded deposit method
			acc.deposit(amount, interest); 
			System.out.printf("Deposit with interest successful. New balance: $%.2f%n", acc.getBalance());
		} catch (RuntimeException ex) {
			System.out.println("Deposit failed: " + ex.getMessage());
		}
	}

	private static void withdrawFlow(Scanner in) {
		BankAccount_AK acc = chooseAccount(in);
		if (acc == null) return;

		System.out.printf("Selected Account: %s%n", acc.toString());
		double amount = readDouble(in, "Enter withdrawal amount: ");
		try {
			// This call is polymorphic. If 'acc' is a SavingsAccount_AK, its
			// overridden withdraw() method will be called.
			acc.withdraw(amount); 
			System.out.printf("Withdrawal successful. New balance: $%.2f%n", acc.getBalance());
		} catch (RuntimeException ex) {
			System.out.println("Withdrawal failed: " + ex.getMessage());
		}
	}

	private static void listAccounts() {
		if (count == 0) {
			System.out.println("No accounts to list.");
			return;
		}
		System.out.println("\n#   Type     Owner             Account#    Balance");
		System.out.println("-----------------------------------------------------");
		for (int i = 0; i < count; i++) {
			BankAccount_AK acc = ACCOUNTS[i];
			String type = (acc instanceof SavingsAccount_AK) ? "SAV" : "BNK";
			String owner = acc.getFirstName() + " " + acc.getLastName();
			System.out.printf("%-3d %-7s  %-16s  %-10s $%.2f%n", i + 1, type, owner, acc.getAccountNumber(), acc.getBalance());
		}
	}

	// --- Helper methods for input ---

	private static BankAccount_AK chooseAccount(Scanner in) {
		if (count == 0) {
			System.out.println("No accounts have been created yet.");
			return null;
		}
		listAccounts();
		int idx = readInt(in, "Pick account # (0 to cancel): ", 0, count);
		if (idx == 0) {
			System.out.println("Operation cancelled.");
			return null;
		}
		return ACCOUNTS[idx - 1];
	}

	private static String readLine(Scanner in, String prompt) {
		System.out.print(prompt);
		String s = in.nextLine().trim();
		while (s.isBlank()) {
			System.out.print("Input cannot be empty. Please try again: ");
			s = in.nextLine().trim();
		}
		return s;
	}

	private static int readInt(Scanner in, String prompt, int min, int max) {
		System.out.print(prompt);
		while (true) {
			try {
				int v = Integer.parseInt(in.nextLine().trim());
				if (v < min || v > max) {
					System.out.printf("Please enter a whole number between %d and %d: ", min, max);
				} else {
					return v;
				}
			} catch (NumberFormatException e) {
				System.out.printf("Invalid input. Please enter a whole number: ");
			}
		}
	}

	private static double readDouble(Scanner in, String prompt) {
		System.out.print(prompt);
		while (true) {
			try {
				return Double.parseDouble(in.nextLine().trim());
			} catch (NumberFormatException e) {
				System.out.print("Invalid input. Please enter a number: ");
			}
		}
	}
}