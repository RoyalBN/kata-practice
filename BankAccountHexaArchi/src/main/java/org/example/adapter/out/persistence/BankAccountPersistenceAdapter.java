package org.example.adapter.out.persistence;

import org.example.adapter.out.persistence.mapper.BankAccountMapper;
import org.example.domain.model.BankAccount;
import org.example.domain.port.out.BankAccountRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class BankAccountPersistenceAdapter implements BankAccountRepository {

    private final SpringDataBankAccountRepository springRepo;
    private final BankAccountMapper mapper;

    public BankAccountPersistenceAdapter(SpringDataBankAccountRepository springRepo, BankAccountMapper mapper) {
        this.springRepo = springRepo;
        this.mapper = mapper;
    }

    @Override
    public BankAccount save(BankAccount bankAccount) {
        BankAccountEntity entity = mapper.toEntity(bankAccount);
        BankAccountEntity savedEntity = springRepo.save(entity);
        return mapper.toDomain(savedEntity);
    }

    @Override
    public Optional<BankAccount> findByAccountId(UUID accountId) {
        return springRepo.findById(accountId).map(mapper::toDomain);
    }
}
