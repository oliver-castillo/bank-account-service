package com.bank.accountservice.service;

import com.bank.accountservice.model.dto.request.transaction.TransactionRequest;
import com.bank.accountservice.model.dto.response.OperationResponse;
import reactor.core.publisher.Mono;

public interface TransactionService<T extends TransactionRequest> {
    Mono<OperationResponse> makeTransaction(T request);
}
