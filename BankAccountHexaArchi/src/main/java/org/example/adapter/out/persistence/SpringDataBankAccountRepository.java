package org.example.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SpringDataBankAccountRepository extends JpaRepository<BankAccountEntity, UUID> {
}
