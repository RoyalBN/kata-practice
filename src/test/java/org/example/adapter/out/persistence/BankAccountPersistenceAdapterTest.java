package org.example.adapter.out.persistence;

import org.example.adapter.out.persistence.mapper.BankAccountMapper;
import org.example.domain.model.AccountType;
import org.example.domain.model.BankAccount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.swing.text.html.Option;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BankAccountPersistenceAdapterTest {

    @Mock
    private SpringDataBankAccountRepository springRepo;

    @Mock
    private BankAccountMapper mapper;

    @InjectMocks
    private BankAccountPersistenceAdapter adapter;

    @BeforeEach
    void setUp() {
    }

    @Test
    @DisplayName("Save successfully and return domain object")
    void should_return_domain_object_when_saving_to_entity() {
        // Arrange
        BankAccount domainAccount = new BankAccount(AccountType.CURRENT, new BigDecimal(100), BigDecimal.ZERO);
        BankAccountEntity entity = new BankAccountEntity();

        when(mapper.toEntity(domainAccount)).thenReturn(entity);
        when(springRepo.save(entity)).thenReturn(entity);
        when(mapper.toDomain(entity)).thenReturn(domainAccount);

        // Act
        BankAccount result = adapter.save(domainAccount);

        // Assert
        assertThat(result).isEqualTo(domainAccount);
        verify(springRepo, times(1)).save(entity);
    }

    @Test
    @DisplayName("Find account by ID and return domain object")
    void should_return_domain_object_when_account_by_id_is_found() {
        // Arrange
        UUID accountId = UUID.randomUUID();
        BankAccount domainAccount = new BankAccount(AccountType.CURRENT, new BigDecimal(100), BigDecimal.ZERO);
        BankAccountEntity entity = new BankAccountEntity();

        when(springRepo.findById(accountId)).thenReturn(Optional.of(entity));
        when(mapper.toDomain(entity)).thenReturn(domainAccount);

        // Act
        Optional<BankAccount> result = adapter.findByAccountId(accountId);

        // Assert
        assertThat(result).isNotEmpty();
        verify(springRepo, times(1)).findById(accountId);
    }

    @Test
    @DisplayName("Return empty if account not found")
    void should_return_empty_if_account_by_id_is_not_found() {
        // Arrange
        UUID accountId = UUID.randomUUID();

        when(springRepo.findById(accountId)).thenReturn(Optional.empty());

        // Act
        Optional<BankAccount> result = adapter.findByAccountId(accountId);

        // Assert
        assertThat(result).isEmpty();
        verify(springRepo, times(1)).findById(accountId);
    }


}