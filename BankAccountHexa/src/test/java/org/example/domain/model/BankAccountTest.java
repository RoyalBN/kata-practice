package org.example.domain.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;

public class BankAccountTest {

    private BankAccount currentAccount;
    private BankAccount savingAccount;

    @BeforeEach
    void setUp() {
        currentAccount = new BankAccount(AccountType.CURRENT, new BigDecimal(1000), BigDecimal.ZERO);
        savingAccount = new BankAccount(AccountType.SAVINGS, new BigDecimal(1000), BigDecimal.ZERO);
    }

    // Accounts should have an unique UUID
    @Test
    @DisplayName("Accounts should have an unique UUID")
    void should_validate_unique_uuid_for_each_account() {
        // Act & Assert
        assertThat(currentAccount.getAccountId()).isNotEqualTo(savingAccount.getAccountId());
    }

    @Test
    @DisplayName("[CURRENT] Withdrawal amount cannot be negative")
    void should_throw_exception_if_withdrawal_amount_is_negative_for_current_account() {
        // Act & Assert
        Throwable thrown = catchThrowable(() -> currentAccount.withdraw(currentAccount.getAccountId(), new BigDecimal(-1)));
        assertThat(thrown)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Le montant doit être supérieur à 0");
    }

    @Test
    @DisplayName("[CURRENT] Withdrawal amount cannot exceed balance")
    void should_throw_exception_if_withdrawal_amount_exceeds_balance_for_current_account() {
        // Act & Assert
        Throwable thrown = catchThrowable(() -> currentAccount.withdraw(currentAccount.getAccountId(), new BigDecimal(2000)));
        assertThat(thrown)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Le montant dépasse le solde");
    }

    @Test
    @DisplayName("[CURRENT] Withdrawal amount is added to balance")
    void should_update_balance_after_withdrawal_for_current_account() {
        // Act & Assert
        currentAccount.withdraw(currentAccount.getAccountId(), new BigDecimal(500));
        assertThat(currentAccount.getBalance()).isEqualTo(new BigDecimal(500));
    }

    @Test
    @DisplayName("[SAVING] Withdrawal amount cannot be negative (<= 0)")
    void should_throw_exception_if_withdrawal_amount_is_negative_for_saving_account() {
        // Act & Assert
        Throwable thrown = catchThrowable(() -> savingAccount.withdraw(savingAccount.getAccountId(), new BigDecimal(-1)));
        assertThat(thrown)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Le montant doit être supérieur à 0");
    }

    @Test
    @DisplayName("[SAVING] Withdrawal amount cannot exceed balance")
    void should_throw_exception_if_withdrawal_amount_exceeds_balance_for_saving_account() {
        // Act & Assert
        Throwable thrown = catchThrowable(() -> savingAccount.withdraw(savingAccount.getAccountId(), new BigDecimal(2000)));
        assertThat(thrown)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Le montant dépasse le solde");
    }

    @Test
    @DisplayName("[SAVING] Withdrawal amount is added to balance")
    void should_update_balance_after_withdrawal_for_saving_account() {
        // Act & Assert
        savingAccount.withdraw(savingAccount.getAccountId(), new BigDecimal(500));
        assertThat(savingAccount.getBalance()).isEqualTo(new BigDecimal(500));
    }

}
