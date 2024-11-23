package com.bank.accountservice.service;

import com.bank.accountservice.exception.AlreadyExistsException;
import com.bank.accountservice.mapper.AccountMapper;
import com.bank.accountservice.model.dto.request.AccountRequest;
import com.bank.accountservice.model.dto.response.OperationResponse;
import com.bank.accountservice.model.enums.AccountType;
import com.bank.accountservice.model.enums.ClientType;
import com.bank.accountservice.repository.AccountRepository;
import com.bank.accountservice.util.ResponseMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@RequiredArgsConstructor
@Service
public class DefaultAccountService implements AccountService {
    private final AccountRepository repository;
    private final AccountMapper mapper;

    @Override
    public Mono<OperationResponse> save(AccountType accountType, ClientType clientType, AccountRequest request) {
        return validateClient(request.getClientId(), accountType, clientType)
                .then(repository.save(mapper.toDocument(accountType, clientType, request)))
                .doOnSuccess(document -> log.info("Account {} created successfully", document.getId()))
                .doOnError(error -> log.error("Error creating account: {}", error.getMessage()))
                .doOnTerminate(() -> log.info("Account creation finished"))
                .map(account -> new OperationResponse(ResponseMessage.CREATED_SUCCESSFULLY, HttpStatus.CREATED));
    }


    private Mono<Boolean> validateClient(String clientId, AccountType accountType, ClientType clientType) {
        if (accountType != AccountType.FIXED_TERM_ACCOUNT && (clientType == ClientType.PERSONAL || clientType == ClientType.PERSONAL_VIP)) {
            switch (accountType) {
                case SAVINGS_ACCOUNT:
                    if (clientType == ClientType.PERSONAL_VIP) {
                        return canCreatePersonalVipSavingsAccount(clientId);
                    } else {
                        return canCreatePersonalSavingsAccount(clientId);
                    }
                case CHECKING_ACCOUNT:
                    return canCreatePersonalCheckingAccount(clientId);
                default:
                    return Mono.just(true);
            }
        } else {
            return Mono.just(true);
        }
    }

    private Mono<Boolean> canCreatePersonalCheckingAccount(String clientId) {
        return repository.findAccountsByClientId(clientId)
                .filter(account -> account.getAccountType() == AccountType.CHECKING_ACCOUNT && account.getClientType() == ClientType.PERSONAL)
                .hasElements()
                .flatMap(exists -> {
                    if (Boolean.TRUE.equals(exists)) {
                        return Mono.error(new AlreadyExistsException("El Cliente Personal solo puede tener una Cuenta Corriente"));
                    }
                    return Mono.just(false);
                });
    }

    private Mono<Boolean> canCreatePersonalSavingsAccount(String clientId) {
        return repository.findAccountsByClientId(clientId)
                .filter(account -> account.getAccountType() == AccountType.SAVINGS_ACCOUNT && account.getClientType() == ClientType.PERSONAL)
                .hasElements()
                .flatMap(exists -> {
                    if (Boolean.TRUE.equals(exists)) {
                        return Mono.error(new AlreadyExistsException("El Cliente Personal solo puede tener una Cuenta de Ahorros"));
                    }
                    return Mono.just(false);
                });
    }

    private Mono<Boolean> canCreatePersonalVipSavingsAccount(String clientId) {
        return repository.findAccountsByClientId(clientId)
                .filter(account -> account.getAccountType() == AccountType.SAVINGS_ACCOUNT && account.getClientType() == ClientType.PERSONAL_VIP)
                .hasElements()
                .flatMap(exists -> {
                    if (Boolean.TRUE.equals(exists)) {
                        return Mono.error(new AlreadyExistsException("El Cliente Personal VIP solo puede tener una Cuenta de Ahorros"));
                    }
                    return Mono.just(false);
                });
    }

}
