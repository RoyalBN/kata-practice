package org.example.adapter.in;

import org.example.adapter.in.dto.CreateBankAccountRequest;
import org.example.adapter.in.dto.WithdrawRequest;
import org.example.domain.model.BankAccount;
import org.example.domain.port.in.BankAccountUseCase;
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
    public ResponseEntity createBankAccount(@RequestBody CreateBankAccountRequest request) {
        BankAccount bankAccount = bankAccountUseCase.createAccount(request.accountType(), request.balance(), request.overdraftLimit());
        return ResponseEntity.ok(bankAccount);
    }

    @PostMapping("/{accountId}/withdraw")
    public ResponseEntity withdraw(
            @PathVariable("accountId") UUID accountId,
            @RequestBody WithdrawRequest request
    ) {
        bankAccountUseCase.withdraw(accountId, request.amount());
        return ResponseEntity.ok().build();
    }
}
