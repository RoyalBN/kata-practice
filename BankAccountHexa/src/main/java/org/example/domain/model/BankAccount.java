package org.example.domain.model;

import java.math.BigDecimal;
import java.util.UUID;

public class BankAccount {
    private final UUID accountId;
    private final AccountType accountType;
    private BigDecimal balance;
    private BigDecimal overdraftLimit;

    public BankAccount(AccountType accountType, BigDecimal balance, BigDecimal overdraftLimit) {
        this.accountId = UUID.randomUUID();
        this.accountType = accountType;
        this.balance = balance;
        this.overdraftLimit = overdraftLimit;
    }

    public UUID getAccountId() {
        return accountId;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public BigDecimal getOverdraftLimit() {
        return overdraftLimit;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void withdraw(UUID accountId, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Le montant doit être supérieur à 0");
        }

        if (amount.compareTo(balance) > 0) {
            throw new IllegalArgumentException("Le montant dépasse le solde");
        }

        if (amount.compareTo(balance) < 0) {
            balance = balance.subtract(amount);
        }

    }
}
