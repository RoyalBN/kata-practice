package org.example.adapter.in.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.example.domain.model.AccountType;
import java.math.BigDecimal;

public record CreateBankAccountRequest(
        @NotNull
        AccountType accountType,
        @Positive
        BigDecimal balance,
        BigDecimal overdraftLimit
) {}
