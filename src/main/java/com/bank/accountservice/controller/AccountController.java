package com.bank.accountservice.controller;

import com.bank.accountservice.model.dto.request.AccountRequest;
import com.bank.accountservice.model.dto.response.AccountResponse;
import com.bank.accountservice.model.dto.response.OperationResponse;
import com.bank.accountservice.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
@RequestMapping
public class AccountController {
    private final AccountService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<OperationResponse> save(@RequestBody @Valid AccountRequest request) {
        return service.save(request);
    }

    @GetMapping(value = "client/{clientId}")
    @ResponseStatus(HttpStatus.OK)
    public Flux<AccountResponse> findByClientId(@PathVariable String clientId) {
        return service.findByClientId(clientId);
    }

    @GetMapping(value = "client/{clientId}/account/{accountNumber}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<AccountResponse> findByAccountNumber(@PathVariable String clientId, @PathVariable String accountNumber) {
        return service.findAccountsByClientIdAndAccountNumber(clientId, accountNumber);
    }
}

