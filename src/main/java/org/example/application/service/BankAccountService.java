package org.example.application.service;

import org.example.application.exception.AccountNotFoundException;
import org.example.domain.model.AccountType;
import org.example.domain.model.BankAccount;
import org.example.domain.port.in.BankAccountUseCase;
import org.example.domain.port.out.BankAccountRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class BankAccountService implements BankAccountUseCase {

    private final BankAccountRepository bankAccountRepository;

    public BankAccountService(BankAccountRepository bankAccountRepository) {
        this.bankAccountRepository = bankAccountRepository;
    }

    @Override
    public BankAccount createAccount(AccountType accountType, BigDecimal balance, BigDecimal overdraftLimit) {
        UUID newAccountId = UUID.randomUUID();
        BankAccount account = new BankAccount(newAccountId, accountType, balance, overdraftLimit);
        return bankAccountRepository.save(account);
    }

    @Override
    public BankAccount withdraw(UUID accountId, BigDecimal amount) {
        BankAccount account = bankAccountRepository.findByAccountId(accountId)
                .orElseThrow(() -> new AccountNotFoundException("Le compte n'a pas été trouvé"));

        account.withdraw(amount);
        return bankAccountRepository.save(account);
    }
}
