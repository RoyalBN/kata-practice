package org.example.domain.model;

import org.example.domain.exception.InvalidWithdrawalAmountException;
import org.example.domain.exception.OverdraftLimitExceededException;

import java.math.BigDecimal;
import java.util.UUID;

public class BankAccount {
    private final UUID accountId;
    private final AccountType accountType;
    private BigDecimal balance;
    private BigDecimal overdraftLimit;

    public BankAccount(UUID accountId, AccountType accountType, BigDecimal balance, BigDecimal overdraftLimit) {
        this.accountId = accountId;
        this.accountType = accountType;
        this.balance = balance;
        this.overdraftLimit = AccountType.SAVINGS.equals(accountType) ? BigDecimal.ZERO : overdraftLimit;
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

    public void withdraw(BigDecimal amountToWithdraw) {
        if (amountToWithdraw == null || amountToWithdraw.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Le montant doit être supérieur à 0");
        }

        switch (accountType) {
            case CURRENT -> validateWithdrawForCurrentAccount(amountToWithdraw);
            case SAVINGS -> validateWithdrawForSavingAccount(amountToWithdraw);
        }
        balance = balance.subtract(amountToWithdraw);
    }

    public void validateWithdrawForCurrentAccount(BigDecimal amountToWithdraw){
        if (amountToWithdraw.compareTo(balance.add(overdraftLimit)) > 0) {
            throw new OverdraftLimitExceededException("Le montant dépasse la limite de découvert");
        }
    }

    public void validateWithdrawForSavingAccount(BigDecimal amountToWithdraw){
        if (amountToWithdraw.compareTo(balance) > 0) {
            throw new InvalidWithdrawalAmountException("Le montant dépasse le solde");
        }
    }
}