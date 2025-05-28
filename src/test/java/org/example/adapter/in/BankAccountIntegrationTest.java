package org.example.adapter.in;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.adapter.in.dto.BankAccountResponse;
import org.example.adapter.in.dto.CreateBankAccountRequest;
import org.example.adapter.in.dto.CreateBankAccountResponse;
import org.example.adapter.in.dto.WithdrawRequest;
import org.example.adapter.out.persistence.SpringDataBankAccountRepository;
import org.example.domain.model.AccountType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Transactional
public class BankAccountIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SpringDataBankAccountRepository repository;

    private UUID createAccount(AccountType type, BigDecimal balance, BigDecimal overdraft) throws Exception {
        var request = new CreateBankAccountRequest(type, balance, overdraft);

        MvcResult result = mockMvc.perform(post("/api/accounts/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andReturn();

        return objectMapper.readValue(result.getResponse().getContentAsString(), CreateBankAccountResponse.class).accountId();
    }

    @Test
    @DisplayName("Create and withdraw from current account with overdraft limit")
    void should_create_and_withdraw_from_a_current_account() throws Exception {
        // 1. Create account
        CreateBankAccountRequest createAccountRequest = new CreateBankAccountRequest(
                AccountType.CURRENT,
                new BigDecimal("1000"),
                new BigDecimal("200")
        );

        MvcResult accountCreatedResult = mockMvc.perform(post("/api/accounts/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createAccountRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.accountId").exists())
                .andExpect(jsonPath("$.accountType").value("CURRENT"))
                .andExpect(jsonPath("$.balance").value(1000))
                .andReturn();

        CreateBankAccountResponse accountCreatedResponse = objectMapper.readValue(accountCreatedResult.getResponse().getContentAsString(), CreateBankAccountResponse.class);
        UUID accountId = accountCreatedResponse.accountId();

        // 2. Withdraw
        WithdrawRequest withdrawRequest = new WithdrawRequest(new BigDecimal("100"));

        MvcResult withdrawResult = mockMvc.perform(post("/api/accounts/{accountId}/withdraw", accountId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(withdrawRequest)))
                .andExpect(status().isOk())
                .andReturn();

        BankAccountResponse withdrawResponse = objectMapper.readValue(withdrawResult.getResponse().getContentAsString(), BankAccountResponse.class);

        // 3. Verify balance
        assertThat(withdrawResponse.balance()).isEqualTo(new BigDecimal("900"));
        assertThat(accountCreatedResponse.accountId()).isEqualTo(withdrawResponse.accountId());
        assertThat(accountCreatedResponse.accountType()).isEqualTo(withdrawResponse.accountType());
        assertThat(accountCreatedResponse.overdraftLimit()).isEqualTo(withdrawResponse.overdraftLimit());
    }

    // Create and withdraw from savings account
    @Test
    @DisplayName("Create and withdraw from savings account")
    void should_create_and_withdraw_from_a_savings_account() throws Exception {
        // 1. Create account
        CreateBankAccountRequest createAccountRequest = new CreateBankAccountRequest(
                AccountType.SAVINGS,
                new BigDecimal("1000"),
                new BigDecimal("0")
        );

        MvcResult accountCreatedResult = mockMvc.perform(post("/api/accounts/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createAccountRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.accountId").exists())
                .andExpect(jsonPath("$.accountType").value("SAVINGS"))
                .andExpect(jsonPath("$.balance").value(1000))
                .andReturn();

        CreateBankAccountResponse accountCreatedResponse = objectMapper.readValue(accountCreatedResult.getResponse().getContentAsString(), CreateBankAccountResponse.class);
        UUID accountId = accountCreatedResponse.accountId();

        // 2. Withdraw
        WithdrawRequest withdrawRequest = new WithdrawRequest(new BigDecimal("100"));

        MvcResult withdrawResult = mockMvc.perform(post("/api/accounts/{accountId}/withdraw", accountId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(withdrawRequest)))
                .andExpect(status().isOk())
                .andReturn();

        BankAccountResponse withdrawResponse = objectMapper.readValue(withdrawResult.getResponse().getContentAsString(), BankAccountResponse.class);

        // 3. Verify balance
        assertThat(withdrawResponse.balance()).isEqualTo(new BigDecimal("900"));
        assertThat(accountCreatedResponse.accountId()).isEqualTo(withdrawResponse.accountId());
        assertThat(accountCreatedResponse.accountType()).isEqualTo(withdrawResponse.accountType());
        assertThat(accountCreatedResponse.overdraftLimit()).isEqualTo(withdrawResponse.overdraftLimit());
    }

    // Withdraw from current account within overdraft limit
    @Test
    @DisplayName("Allow withdrawal from current account within overdraft limit")
    void should_allow_overdraft_limit_when_withdrawing_from_current_account() throws Exception {
        // 1. Create account
        CreateBankAccountRequest createAccountRequest = new CreateBankAccountRequest(
                AccountType.CURRENT,
                new BigDecimal("1000"),
                new BigDecimal("200")
        );

        MvcResult accountCreatedResult = mockMvc.perform(post("/api/accounts/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createAccountRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.accountId").exists())
                .andExpect(jsonPath("$.accountType").value("CURRENT"))
                .andExpect(jsonPath("$.balance").value(1000))
                .andReturn();

        CreateBankAccountResponse accountCreatedResponse = objectMapper.readValue(accountCreatedResult.getResponse().getContentAsString(), CreateBankAccountResponse.class);
        UUID accountId = accountCreatedResponse.accountId();

        // 2. Withdraw
        WithdrawRequest withdrawRequest = new WithdrawRequest(new BigDecimal("1100"));

        MvcResult withdrawResult = mockMvc.perform(post("/api/accounts/{accountId}/withdraw", accountId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(withdrawRequest)))
                .andExpect(status().isOk())
                .andReturn();

        BankAccountResponse withdrawResponse = objectMapper.readValue(withdrawResult.getResponse().getContentAsString(), BankAccountResponse.class);

        // 3. Verify balance
        assertThat(withdrawResponse.balance()).isEqualTo(new BigDecimal("-100"));
        assertThat(accountCreatedResponse.accountId()).isEqualTo(withdrawResponse.accountId());
        assertThat(accountCreatedResponse.accountType()).isEqualTo(withdrawResponse.accountType());
        assertThat(accountCreatedResponse.overdraftLimit()).isEqualTo(withdrawResponse.overdraftLimit());
    }

    @Test
    @DisplayName("Should return 400 when creating account with invalid request")
    void should_fail_when_creating_account_with_invalid_request() throws Exception {
        // 1. Prepare invalid request
        CreateBankAccountRequest invalidRequest = new CreateBankAccountRequest(
                null, // Type de compte invalide
                new BigDecimal("1000"),
                new BigDecimal("200")
        );

        // 2. Create account with invalid request
        mockMvc.perform(post("/api/accounts/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should return 400 when withdrawing with negative amount")
    void should_fail_when_withdrawing_negative_amount() throws Exception {
        // Création d'un compte
        UUID accountId = createAccount(AccountType.CURRENT, new BigDecimal("1000"), new BigDecimal("200"));

        // Tentative de retrait avec un montant négatif
        WithdrawRequest invalidWithdrawRequest = new WithdrawRequest(new BigDecimal("-100"));

        mockMvc.perform(post("/api/accounts/{accountId}/withdraw", accountId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidWithdrawRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should return 422 when withdrawing amount exceeding balance and overdraft")
    void should_fail_when_withdrawing_more_than_balance_plus_overdraft() throws Exception {
        // 1. Create account with specific balance and overdraft
        UUID accountId = createAccount(AccountType.CURRENT, new BigDecimal("1000"), new BigDecimal("200"));

        // 2. Prepare withdrawal request exceeding balance and overdraft
        WithdrawRequest withdrawRequest = new WithdrawRequest(new BigDecimal("1201")); // 1000 + 200 = 1200 max

        // 3. Withdraw exceeding balance and overdraft
        MvcResult result = mockMvc.perform(post("/api/accounts/{accountId}/withdraw", accountId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(withdrawRequest)))
                .andExpect(status().isUnprocessableEntity())
                .andReturn();

        // 4. Verify error response
        Map<String, String> response = objectMapper.readValue(result.getResponse().getContentAsString(),
                new TypeReference<Map<String, String>>() {});
        assertThat(response).containsKey("message");
        assertThat(response.get("message")).isNotEmpty();
    }

    @Test
    @DisplayName("Should return 404 when account not found for withdrawal")
    void should_fail_when_account_not_found() throws Exception {
        // 1. Prepare non-existent account ID
        UUID nonExistentAccountId = UUID.randomUUID();

        // 2. Prepare withdrawal request
        WithdrawRequest withdrawRequest = new WithdrawRequest(new BigDecimal("100"));

        // 3. Try to withdraw from non-existent account
        mockMvc.perform(post("/api/accounts/{accountId}/withdraw", nonExistentAccountId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(withdrawRequest)))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Should return 400 when creating account with negative balance")
    void should_fail_when_creating_account_with_negative_balance() throws Exception {
        CreateBankAccountRequest request = new CreateBankAccountRequest(
                AccountType.CURRENT,
                new BigDecimal("-100"), // Solde négatif
                new BigDecimal("200")
        );

        mockMvc.perform(post("/api/accounts/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should return 400 when creating account with invalid JSON")
    void should_fail_when_creating_account_with_invalid_json() throws Exception {
        String invalidJson = "{ invalid: json }";

        mockMvc.perform(post("/api/accounts/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJson))
                .andExpect(status().isBadRequest());
    }


}
