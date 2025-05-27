package org.example.adapter.in.dto;

import org.example.domain.model.AccountType;
import java.math.BigDecimal;

public record CreateBankAccountRequest(
        AccountType accountType,
        BigDecimal balance,
        BigDecimal overdraftLimit
) {}
