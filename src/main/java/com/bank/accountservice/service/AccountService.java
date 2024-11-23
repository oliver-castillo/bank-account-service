package com.bank.accountservice.service;

import com.bank.accountservice.model.dto.request.AccountRequest;
import com.bank.accountservice.model.dto.response.AccountResponse;
import com.bank.accountservice.model.dto.response.OperationResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AccountService {
    Mono<OperationResponse> save(AccountRequest request);

    Flux<AccountResponse> findByClientId(String clientId);

    Mono<AccountResponse> findAccountsByClientIdAndAccountNumber(String clientId, String accountNumber);
}
