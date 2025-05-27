package org.example.application.service;

import org.example.domain.model.AccountType;
import org.example.domain.model.BankAccount;
import org.example.domain.port.in.BankAccountUseCase;
import org.example.domain.port.out.BankAccountRepository;

import java.math.BigDecimal;
import java.util.UUID;

public class BankAccountService implements BankAccountUseCase {

    private final BankAccountRepository bankAccountRepository;

    public BankAccountService(BankAccountRepository bankAccountRepository) {
        this.bankAccountRepository = bankAccountRepository;
    }

    @Override
    public BankAccount createAccount(AccountType accountType, BigDecimal balance, BigDecimal overdraftLimit) {
        BankAccount account = new BankAccount(accountType, balance, overdraftLimit);
        return bankAccountRepository.save(account);
    }

    @Override
    public void withdraw(UUID accountId, BigDecimal amount) {
        BankAccount account = bankAccountRepository.findByAccountId(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Le compte n'a pas été trouvé"));

        account.withdraw(accountId, amount);
        bankAccountRepository.save(account);
    }


}
