package com.bank.accountservice.controller;

import com.bank.accountservice.model.dto.request.transaction.DepositRequest;
import com.bank.accountservice.model.dto.request.transaction.TransferRequest;
import com.bank.accountservice.model.dto.request.transaction.WithdrawalRequest;
import com.bank.accountservice.model.dto.response.OperationResponse;
import com.bank.accountservice.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "transactions")
public class TransactionController {
    private final TransactionService<TransferRequest> transferService;
    private final TransactionService<WithdrawalRequest> withdrawalService;
    private final TransactionService<DepositRequest> depositService;

    @PostMapping(value = "transfer")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<OperationResponse> save(@RequestBody @Valid TransferRequest request) {
        return transferService.save(request);
    }

    @PostMapping(value = "withdrawal")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<OperationResponse> save(@RequestBody @Valid WithdrawalRequest request) {
        return withdrawalService.save(request);
    }

    @PostMapping(value = "deposit")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<OperationResponse> save(@RequestBody @Valid DepositRequest request) {
        return depositService.save(request);
    }
}
