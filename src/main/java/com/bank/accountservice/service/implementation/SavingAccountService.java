package com.bank.accountservice.service.implementation;

import com.bank.accountservice.mapper.AccountMapper;
import com.bank.accountservice.model.dto.request.SavingAccountRequest;
import com.bank.accountservice.model.dto.response.OperationResponse;
import com.bank.accountservice.repository.SavingAccountRepository;
import com.bank.accountservice.service.AccountService;
import com.bank.accountservice.util.ResponseMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@RequiredArgsConstructor
@Service
public class SavingAccountService implements AccountService<SavingAccountRequest> {
    private final SavingAccountRepository repository;
    private final AccountMapper mapper;

    @Override
    public Mono<OperationResponse> save(SavingAccountRequest request) {
        return repository.save(mapper.toDocument(request))
                .doOnSuccess(document -> log.info("Account {} created successfully", document.getId()))
                .doOnError(error -> log.error("Error creating account", error))
                .doOnTerminate(() -> log.info("Account creation finished"))
                .map(account -> new OperationResponse(ResponseMessage.CREATED_SUCCESSFULLY, HttpStatus.CREATED));
    }
}
