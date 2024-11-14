package com.bank.accountservice.controller;

import com.bank.accountservice.model.dto.response.OperationResponse;
import com.bank.accountservice.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public abstract class BaseAccountController<R> {
    private final AccountService<R> accountService;

    @PostMapping("/save")
    public Mono<ResponseEntity<OperationResponse>> saveAccount(@RequestBody @Valid R request) {
        return accountService.save(request)
                .map(response -> new ResponseEntity<>(response, HttpStatus.CREATED));
    }
}
