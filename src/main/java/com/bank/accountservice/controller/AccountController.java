package com.bank.accountservice.controller;

import com.bank.accountservice.model.dto.request.AccountRequest;
import com.bank.accountservice.model.dto.response.OperationResponse;
import com.bank.accountservice.model.enums.AccountType;
import com.bank.accountservice.model.enums.ClientType;
import com.bank.accountservice.service.AccountService;
import com.bank.accountservice.util.ResponseMessage;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
@RequestMapping
public class AccountController {
    private final AccountService service;

    /**
     * Tipos de cuentas
     * 1. Personal - Cuenta de ahorro (Savings Account)
     * 2. Personal - Cuenta corriente (Checking Account)
     * 3. Personal - Cuenta plazo fijo (Fixed Term Account)
     * 4. Personal - Cuenta de ahorro VIP (VIP Savings Account)
     * 5. Empresarial - Cuenta corriente (Checking Account)
     * 6. Empresarial - Cuenta corriente Pyme (Pyme Checking Account)
     */

    @PostMapping
    public Mono<OperationResponse> save(@RequestBody @Valid AccountRequest request) {
        return switch (request.getType()) {
            case 1 -> service.save(AccountType.SAVINGS_ACCOUNT, ClientType.PERSONAL, request);
            case 2 -> service.save(AccountType.CHECKING_ACCOUNT, ClientType.PERSONAL, request);
            case 3 -> service.save(AccountType.FIXED_TERM_ACCOUNT, ClientType.PERSONAL, request);
            case 4 -> service.save(AccountType.SAVINGS_ACCOUNT, ClientType.PERSONAL_VIP, request);
            case 5 -> service.save(AccountType.CHECKING_ACCOUNT, ClientType.BUSINESS, request);
            case 6 -> service.save(AccountType.CHECKING_ACCOUNT, ClientType.BUSINESS_PYME, request);
            default -> Mono.just(new OperationResponse(ResponseMessage.REQUIREMENT_NOT_MET, HttpStatus.BAD_REQUEST));
        };
    }
}

