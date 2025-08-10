package org.example.adapter.out.persistence;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.domain.model.AccountType;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "bank_accounts")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BankAccountEntity {

    @Id
    private UUID id;

    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    private BigDecimal balance;

    private BigDecimal overdraftLimit;

}
