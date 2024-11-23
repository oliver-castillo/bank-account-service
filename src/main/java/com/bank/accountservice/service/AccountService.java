package com.bank.accountservice.service;

import com.bank.accountservice.model.dto.request.AccountRequest;
import com.bank.accountservice.model.dto.response.OperationResponse;
import com.bank.accountservice.model.enums.AccountType;
import com.bank.accountservice.model.enums.ClientType;
import reactor.core.publisher.Mono;

public interface AccountService {
    Mono<OperationResponse> save(AccountType accountType, ClientType clientType, AccountRequest request);
}
