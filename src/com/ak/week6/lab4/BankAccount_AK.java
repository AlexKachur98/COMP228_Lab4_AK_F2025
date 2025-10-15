package com.ak.week6.lab4;

import java.util.Locale;

/*
 * File: BankAccount_AK.java
 * @author Alex Kachur
 * @since 2025-10-9
 * @purpose Base abstract bank account with owner details and validated balance operations.
 */

/**
 * An abstract bank account with owner details, an immutable account number, and
 * a current balance. Provides validated deposit and withdraw operations.
 */
public abstract class BankAccount_AK {

	// Final attributes as required by the lab instructions
	private final String firstName;
	private final String lastName;
	private final String accountNumber;

	private double balance;

	/**
	 * Constructs a new bank account.
	 *
	 * @param firstName      the first name of the account holder
	 * @param lastName       the last name of the account holder
	 * @param accountNumber  non-empty identifier
	 * @param initialBalance starting balance, must be >= 0
	 * @throws IllegalArgumentException if inputs are invalid
	 */
	public BankAccount_AK(String firstName, String lastName, String accountNumber, double initialBalance) {
		// Validation for constructor parameters
		if (firstName == null || firstName.isBlank()) {
			throw new IllegalArgumentException("firstName must be non-empty");
		}
		if (lastName == null || lastName.isBlank()) {
			throw new IllegalArgumentException("lastName must be non-empty");
		}
		if (accountNumber == null || accountNumber.isBlank()) {
			throw new IllegalArgumentException("accountNumber must be non-empty");
		}
		if (initialBalance < 0) {
			throw new IllegalArgumentException("initial balance must be >= 0");
		}
		this.firstName = firstName.trim();
		this.lastName = lastName.trim();
		this.accountNumber = accountNumber.trim();
		this.balance = initialBalance;
	}

	// Accessor (get) methods for the new final fields
	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public double getBalance() {
		return balance;
	}

	/**
	 * Deposits money.
	 *
	 * @param amount amount to deposit; must be > 0
	 * @throws IllegalArgumentException if amount <= 0
	 */
	public void deposit(double amount) {
		if (amount <= 0) {
			throw new IllegalArgumentException("deposit amount must be > 0");
		}
		balance += amount;
	}

	/**
	 * Method Overloading: Deposits money with interest.
	 *
	 * @param amount       amount to deposit; must be > 0
	 * @param interestRate interest rate to apply; must be > 0
	 * @throws IllegalArgumentException if amount or interestRate are invalid
	 */
	public void deposit(double amount, double interestRate) {
		if (amount <= 0) {
			throw new IllegalArgumentException("deposit amount must be > 0");
		}
		if (interestRate <= 0) {
			throw new IllegalArgumentException("interest rate must be > 0");
		}
		// Calculation includes base amount + interest earned
		double interest = amount * interestRate;
		balance += amount + interest;
	}

	/**
	 * Withdraws money.
	 *
	 * @param amount amount to withdraw; must be > 0 and <= balance
	 */
	public void withdraw(double amount) {
		if (amount <= 0) {
			throw new IllegalArgumentException("withdraw amount must be > 0");
		}
		if (amount > balance) {
			throw new IllegalStateException("insufficient funds");
		}
		balance -= amount;
	}

	@Override
	public String toString() {
		return String.format(Locale.US, "Owner: %s %s, Account# %s, Balance=$%.2f", firstName, lastName, accountNumber,
				balance);
	}
}