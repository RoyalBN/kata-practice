package org.example.domain.port.in;

import org.example.domain.model.AccountType;
import org.example.domain.model.BankAccount;

import java.math.BigDecimal;
import java.util.UUID;

public interface BankAccountUseCase {
    BankAccount createAccount(AccountType accountType, BigDecimal balance, BigDecimal overdraftLimit);
    void withdraw(UUID accountId, BigDecimal amount);
}
