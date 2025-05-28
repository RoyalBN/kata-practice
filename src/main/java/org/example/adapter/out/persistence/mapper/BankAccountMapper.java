package org.example.adapter.out.persistence.mapper;

import org.example.adapter.out.persistence.BankAccountEntity;
import org.example.domain.model.BankAccount;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class BankAccountMapper {

    public BankAccountEntity toEntity(BankAccount domain) {
        return new BankAccountEntity(
                domain.getAccountId() != null ? domain.getAccountId() : UUID.randomUUID(),
                domain.getAccountType(),
                domain.getBalance(),
                domain.getOverdraftLimit()
        );
    }

    public BankAccount toDomain(BankAccountEntity entity) {
        return new BankAccount(entity.getId(), entity.getAccountType(), entity.getBalance(), entity.getOverdraftLimit());
    }
}
