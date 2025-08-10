package org.example.adapter.in.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import org.example.domain.model.AccountType;

import java.math.BigDecimal;

public record CreateBankAccountRequest(
        @NotNull
        AccountType accountType,
        @PositiveOrZero
        BigDecimal balance,
        @PositiveOrZero
        BigDecimal overdraftLimit
) {}
