package org.example.adapter.in.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record WithdrawRequest(
        @NotNull(message = "Le montant est obligatoire")
        @DecimalMin(value = "0.01", inclusive = true, message = "Le montant doit être supérieur à 0")
        BigDecimal amount) {}

