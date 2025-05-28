package org.example.domain.model;

import org.example.domain.exception.InvalidWithdrawalAmountException;
import org.example.domain.exception.OverdraftLimitExceededException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;

class BankAccountTest {

    private BankAccount currentAccount;
    private BankAccount savingAccount;

    @BeforeEach
    void setUp() {
        currentAccount = new BankAccount(UUID.randomUUID(), AccountType.CURRENT, new BigDecimal(1000), new BigDecimal(500));
        savingAccount = new BankAccount(UUID.randomUUID(), AccountType.SAVINGS, new BigDecimal(1000), BigDecimal.ZERO);
    }

    @Test
    @DisplayName("[CREATE] Create new account with initial balance")
    void should_create_account_with_initial_balance_and_type() {
        BankAccount account = new BankAccount(UUID.randomUUID(), AccountType.CURRENT, new BigDecimal(500.00), BigDecimal.ZERO);

        assertThat(account.getAccountId()).isNotNull();
        assertThat(account.getAccountType()).isEqualTo(AccountType.CURRENT);
        assertThat(account.getBalance()).isEqualTo(new BigDecimal(500.00));
    }


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
        Throwable thrown = catchThrowable(() -> currentAccount.withdraw(new BigDecimal(-1)));
        assertThat(thrown)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Le montant doit être supérieur à 0");
    }

    @Test
    @DisplayName("[CURRENT] Withdrawal amount is added to balance")
    void should_decrease_balance_with_valid_current_account_withdrawal() {
        // Act & Assert
        currentAccount.withdraw(new BigDecimal(500));
        assertThat(currentAccount.getBalance()).isEqualTo(new BigDecimal(500));
    }

    @Test
    @DisplayName("[SAVING] Withdrawal amount cannot be negative (<= 0)")
    void should_throw_exception_if_withdrawal_amount_is_negative_for_saving_account() {
        // Act & Assert
        Throwable thrown = catchThrowable(() -> savingAccount.withdraw(new BigDecimal(-1)));
        assertThat(thrown)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Le montant doit être supérieur à 0");
    }

    @Test
    @DisplayName("[SAVING] Withdrawal amount cannot exceed balance")
    void should_throw_exception_if_withdrawal_amount_exceeds_balance_for_saving_account() {
        // Act & Assert
        Throwable thrown = catchThrowable(() -> savingAccount.withdraw(new BigDecimal(2000)));
        assertThat(thrown)
                .isInstanceOf(InvalidWithdrawalAmountException.class)
                .hasMessage("Le montant dépasse le solde");
    }

    @Test
    @DisplayName("[SAVING] Withdrawal amount is added to balance")
    void should_update_balance_after_withdrawal_for_saving_account() {
        // Act & Assert
        savingAccount.withdraw(new BigDecimal(500));
        assertThat(savingAccount.getBalance()).isEqualTo(new BigDecimal(500));
    }

    @Test
    @DisplayName("[OVERDRAFT] Current account can have overdraft limit")
    void should_have_an_overdraft_limit_for_current_account() {
        // Act & Assert
        assertThat(currentAccount.getOverdraftLimit()).isEqualTo(new BigDecimal(500));
    }

    @Test
    @DisplayName("[OVERDRAFT] Savings account cannot have overdraft limit")
    void should_not_have_an_overdraft_limit_for_savings_account() {
        // Act & Assert
        assertThat(savingAccount.getOverdraftLimit()).isEqualTo(BigDecimal.ZERO);
    }

    @Test
    @DisplayName("[OVERDRAFT] Withdraw amount when not exceeding overdraft limit")
    void should_allow_withdrawal_within_overdraft_limit_for_current_account() {
        // Act & Assert
        currentAccount.withdraw(new BigDecimal(500));
        assertThat(currentAccount.getBalance()).isEqualTo(new BigDecimal(500));
    }

    @Test
    @DisplayName("[OVERDRAFT] Throw exception when exceeding overdraft limit")
    void should_throw_exception_when_exceeding_overdraft_limit() {
        // Act & Assert
        Throwable thrown = catchThrowable(() -> currentAccount.withdraw(new BigDecimal(2000)));
        assertThat(thrown)
                .isInstanceOf(OverdraftLimitExceededException.class)
                .hasMessage("Le montant dépasse la limite de découvert");
    }

}
