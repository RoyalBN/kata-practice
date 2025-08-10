package org.example.adapter.in;

import jakarta.validation.Valid;
import org.example.adapter.in.dto.BankAccountResponse;
import org.example.adapter.in.dto.CreateBankAccountRequest;
import org.example.adapter.in.dto.CreateBankAccountResponse;
import org.example.adapter.in.dto.WithdrawRequest;
import org.example.domain.model.BankAccount;
import org.example.domain.port.in.BankAccountUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/accounts")
public class BankAccountController {

    private final BankAccountUseCase bankAccountUseCase;

    public BankAccountController(BankAccountUseCase bankAccountUseCase) {
        this.bankAccountUseCase = bankAccountUseCase;
    }

    @PostMapping("/create")
    public ResponseEntity<CreateBankAccountResponse> createBankAccount(@Valid @RequestBody CreateBankAccountRequest request) {
        BankAccount bankAccount = bankAccountUseCase.createAccount(request.accountType(), request.balance(), request.overdraftLimit());
        var response = new CreateBankAccountResponse(bankAccount.getAccountId(), bankAccount.getAccountType(), bankAccount.getBalance(), bankAccount.getOverdraftLimit());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/{accountId}/withdraw")
    public ResponseEntity<BankAccountResponse> withdraw(
            @PathVariable("accountId") UUID accountId,
            @Valid @RequestBody WithdrawRequest request
    ) {
        BankAccount updatedAccount = bankAccountUseCase.withdraw(accountId, request.amount());
        BankAccountResponse response = BankAccountResponse.from(updatedAccount);
        return ResponseEntity.ok(response);
    }
}
