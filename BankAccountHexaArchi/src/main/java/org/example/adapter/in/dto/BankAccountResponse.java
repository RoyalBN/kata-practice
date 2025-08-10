package org.example.adapter.in.dto;

import org.example.domain.model.AccountType;
import org.example.domain.model.BankAccount;

import java.math.BigDecimal;
import java.util.UUID;

public record BankAccountResponse(
    UUID accountId,
    AccountType accountType,
    BigDecimal balance,
    BigDecimal overdraftLimit
) {
    // Méthode statique pour convertir un BankAccount en BankAccountResponse
    public static BankAccountResponse from(BankAccount account) {
        return new BankAccountResponse(
                account.getAccountId(),
                account.getAccountType(),
                account.getBalance(),
                account.getOverdraftLimit()
        );
    }
}
