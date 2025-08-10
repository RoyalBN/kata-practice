package org.example.domain.port.out;

import org.example.domain.model.BankAccount;

import java.util.Optional;
import java.util.UUID;

public interface BankAccountRepository {
    BankAccount save(BankAccount bankAccount);
    Optional<BankAccount> findByAccountId(UUID accountId);
}
