package com.ak.week6.lab4;

import java.util.Locale;

/*
 * File: SavingsAccount_AK.java
 * @author Alex Kachur
 * @since 2025-10-9
 * @purpose Savings account that enforces a $50 minimum balance after withdrawals.
 */

/**
 * A savings account that blocks any withdrawal which would reduce the balance
 * below $50.
 */
public class SavingsAccount_AK extends BankAccount_AK {

	/** Minimum balance that must remain after any withdrawal. */
	private static final double MIN_BALANCE = 50.0;

	/**
	 * Constructs a new savings account.
	 *
	 * @param firstName      the first name of the account holder
	 * @param lastName       the last name of the account holder
	 * @param accountNumber  non-empty identifier
	 * @param initialBalance starting balance
	 * @throws IllegalArgumentException if inputs are invalid
	 */
	public SavingsAccount_AK(String firstName, String lastName, String accountNumber, double initialBalance) {
		// Explicitly call the superclass constructor. This MUST be the first line.
		super(firstName, lastName, accountNumber, initialBalance);
	}

	/**
	 * Method Overriding: Withdraws money but refuses operations that violate the
	 * $50 minimum.
	 *
	 * @param amount amount to withdraw; must be > 0
	 * @throws IllegalStateException if resulting balance would be < $50
	 */
	@Override
	public void withdraw(double amount) {
		// First, perform the validation specific to this subclass
		double newBalance = getBalance() - amount;
		if (newBalance < MIN_BALANCE) {
			throw new IllegalStateException(String.format(Locale.US,
					"Withdrawal denied: balance would fall below the $%.2f minimum.", MIN_BALANCE));
		}

		// If validation passes, call the superclass's withdraw method to complete the
		// action.
		// This reuses the code from the superclass, which is good practice.
		super.withdraw(amount);
	}
}