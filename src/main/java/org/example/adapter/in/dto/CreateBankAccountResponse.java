package org.example.adapter.in.dto;

import org.example.domain.model.AccountType;

import java.math.BigDecimal;
import java.util.UUID;

public record CreateBankAccountResponse(
    UUID accountId,
    AccountType accountType,
    BigDecimal balance,
    BigDecimal overdraftLimit
) {
}
