package com.bank.accountservice.service;

import com.bank.accountservice.model.dto.response.OperationResponse;
import reactor.core.publisher.Mono;

public interface AccountService<R> {
    Mono<OperationResponse> save(R request);
}