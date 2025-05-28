package org.example.adapter.in;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.adapter.in.dto.CreateBankAccountRequest;
import org.example.adapter.in.dto.WithdrawRequest;
import org.example.domain.model.AccountType;
import org.example.domain.model.BankAccount;
import org.example.domain.port.in.BankAccountUseCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BankAccountController.class)
class BankAccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BankAccountUseCase bankAccountUseCase;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Create a new bank account for current")
    void should_create_a_new_bank_account_for_current() throws Exception {
        // Arrange
        CreateBankAccountRequest request = new CreateBankAccountRequest(
                AccountType.CURRENT,
                new BigDecimal("1000"),
                new BigDecimal("200")
        );
        BankAccount bankAccount = new BankAccount(
                UUID.randomUUID(),
                AccountType.CURRENT,
                new BigDecimal("1000"),
                new BigDecimal("200"));
        when(bankAccountUseCase.createAccount(
                AccountType.CURRENT,
                new BigDecimal("1000"),
                new BigDecimal("200")
        )).thenReturn(bankAccount);

        // Act & Assert
        mockMvc.perform(post("/api/accounts/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.accountId").exists())
                .andExpect(jsonPath("$.accountType").value("CURRENT"))
                .andExpect(jsonPath("$.balance").value(1000));

        verify(bankAccountUseCase, times(1))
                .createAccount(AccountType.CURRENT, new BigDecimal("1000"), new BigDecimal("200"));
    }


    @Test
    @DisplayName("Create a new bank account for savings")
    void should_create_a_new_bank_account_for_savings() throws Exception {
        // Arrange
        CreateBankAccountRequest request = new CreateBankAccountRequest(
                AccountType.SAVINGS,
                new BigDecimal("1000"),
                new BigDecimal("200")
        );
        BankAccount bankAccount = new BankAccount(
                UUID.randomUUID(),
                AccountType.SAVINGS,
                new BigDecimal("1000"),
                new BigDecimal("200"));
        when(bankAccountUseCase.createAccount(
                AccountType.SAVINGS,
                new BigDecimal("1000"),
                new BigDecimal("200")
        )).thenReturn(bankAccount);

        // Act & Assert
        mockMvc.perform(post("/api/accounts/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.accountId").exists())
                .andExpect(jsonPath("$.accountType").value("SAVINGS"))
                .andExpect(jsonPath("$.balance").value(1000));
    }

    @Test
    @DisplayName("Withdraw from a current account")
    void should_withdraw_from_a_current_account() throws Exception {
        // Arrange
        UUID accountId = UUID.randomUUID();
        WithdrawRequest withdrawRequest = new WithdrawRequest(new BigDecimal(100));
        BankAccount updatedAccount = new BankAccount(UUID.randomUUID(), AccountType.CURRENT, new BigDecimal(900), new BigDecimal(200));

        when(bankAccountUseCase.withdraw(accountId, new BigDecimal(100))).thenReturn(updatedAccount);

        // Act & Assert
        mockMvc.perform(post("/api/accounts/{accountId}/withdraw", accountId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(withdrawRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accountId").exists())
                .andExpect(jsonPath("$.accountType").value("CURRENT"))
                .andExpect(jsonPath("$.balance").value(900))
                .andExpect(jsonPath("$.overdraftLimit").value(200));


        verify(bankAccountUseCase, times(1)).withdraw(accountId, new BigDecimal(100));
    }

    @Test
    @DisplayName("Withdraw from a savings account")
    void should_withdraw_from_a_savings_account() throws Exception {
        // Arrange
        UUID accountId = UUID.randomUUID();
        WithdrawRequest withdrawRequest = new WithdrawRequest(new BigDecimal(100));
        BankAccount updatedAccount = new BankAccount(UUID.randomUUID(), AccountType.SAVINGS, new BigDecimal(1000), BigDecimal.ZERO);

        when(bankAccountUseCase.withdraw(accountId, new BigDecimal(100))).thenReturn(updatedAccount);

        // Act & Assert
        mockMvc.perform(post("/api/accounts/{accountId}/withdraw", accountId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(withdrawRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accountId").exists())
                .andExpect(jsonPath("$.accountType").value("SAVINGS"))
                .andExpect(jsonPath("$.balance").value(1000))
                .andExpect(jsonPath("$.overdraftLimit").value(BigDecimal.ZERO));

        verify(bankAccountUseCase, times(1)).withdraw(accountId, new BigDecimal(100));
    }

}