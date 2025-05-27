package org.example.application.service;

import org.example.domain.model.AccountType;
import org.example.domain.model.BankAccount;
import org.example.domain.port.out.BankAccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BankAccountServiceTest {

    @Mock
    private BankAccountRepository bankAccountRepository;

    @InjectMocks
    private BankAccountService bankAccountService;

    private BankAccount currentAccount;
    private BankAccount savingAccount;

    @BeforeEach
    void setUp() {
        currentAccount = new BankAccount(AccountType.CURRENT, new BigDecimal(1000), BigDecimal.ZERO);
        savingAccount = new BankAccount(AccountType.SAVINGS, new BigDecimal(1000), BigDecimal.ZERO);
        bankAccountService = new BankAccountService(bankAccountRepository);
    }

    @Test
    @DisplayName("Create a new bank account for current")
    void should_create_a_new_bank_account_for_current() {
        when(bankAccountRepository.save(any(BankAccount.class))).thenReturn(currentAccount);

        // Act
        BankAccount account = bankAccountService.createAccount(AccountType.CURRENT, new BigDecimal(1000), BigDecimal.ZERO);

        // Assert
        assertThat(account.getAccountId()).isNotNull();
        assertThat(account.getAccountType()).isEqualTo(AccountType.CURRENT);
        assertThat(account.getBalance()).isEqualTo(new BigDecimal(1000));
        verify(bankAccountRepository, times(1)).save(any(BankAccount.class));
    }

    @Test
    @DisplayName("Create a new bank account for current")
    void should_create_a_new_bank_account_for_savings() {
        when(bankAccountRepository.save(any(BankAccount.class))).thenReturn(savingAccount);

        // Act
        BankAccount account = bankAccountService.createAccount(AccountType.SAVINGS, new BigDecimal(1000), BigDecimal.ZERO);

        // Assert
        assertThat(account.getAccountId()).isNotNull();
        assertThat(account.getAccountType()).isEqualTo(AccountType.SAVINGS);
        assertThat(account.getBalance()).isEqualTo(new BigDecimal(1000));
        verify(bankAccountRepository, times(1)).save(any(BankAccount.class));
    }

    // Throw an exception if account is not found
    @Test
    @DisplayName("Throw an exception if account is not found")
    void should_throw_an_exception_if_account_is_not_found() {
        // Arrange
        UUID accountId = UUID.randomUUID();
        when(bankAccountRepository.findByAccountId(accountId)).thenReturn(Optional.empty());

        // Act
        Throwable thrown = catchThrowable(() -> bankAccountService.withdraw(accountId, new BigDecimal(100)));

        // Assert
        assertThat(thrown)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Le compte n'a pas été trouvé");
    }


    // Throw an exception if withdrawal amount is negative
    @Test
    @DisplayName("Throw an exception if withdrawal amount is negative")
    void should_throw_an_exception_if_withdrawal_amount_is_negative() {
        // Arrange
        when(bankAccountRepository.findByAccountId(any(UUID.class))).thenReturn(Optional.of(currentAccount));

        // Act
        Throwable thrown = catchThrowable(() -> bankAccountService.withdraw(currentAccount.getAccountId(), new BigDecimal(-1)));

        // Assert
        assertThat(thrown)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Le montant doit être supérieur à 0");
    }

    // Withdraw from a current account
    @Test
    @DisplayName("Withdraw from a current account")
    void should_withdraw_from_a_current_account() {
        // Arrange
        UUID accountId = UUID.randomUUID();
        BigDecimal byAmountOf = new BigDecimal(100);
        when(bankAccountRepository.findByAccountId(accountId)).thenReturn(Optional.of(currentAccount));

        // Act
        bankAccountService.withdraw(accountId, byAmountOf);

        // Assert
        assertThat(currentAccount.getBalance()).isEqualTo(new BigDecimal(900));
        verify(bankAccountRepository, times(1)).findByAccountId(accountId);
        verify(bankAccountRepository, times(1)).save(currentAccount);
    }

    // Withdraw from a savings account
    @Test
    @DisplayName("Withdraw from a savings account")
    void should_withdraw_from_a_savings_account() {
        // Arrange
        UUID accountId = UUID.randomUUID();
        BigDecimal byAmountOf = new BigDecimal(100);
        when(bankAccountRepository.findByAccountId(accountId)).thenReturn(Optional.of(savingAccount));

        // Act
        bankAccountService.withdraw(accountId, byAmountOf);

        // Assert
        assertThat(savingAccount.getBalance()).isEqualTo(new BigDecimal(900));
        verify(bankAccountRepository, times(1)).findByAccountId(accountId);
        verify(bankAccountRepository, times(1)).save(savingAccount);
    }


}