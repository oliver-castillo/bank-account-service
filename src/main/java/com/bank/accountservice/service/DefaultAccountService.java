package com.bank.accountservice.service;

import com.bank.accountservice.exception.AlreadyExistsException;
import com.bank.accountservice.exception.NotFoundException;
import com.bank.accountservice.mapper.AccountMapper;
import com.bank.accountservice.model.dto.request.account.AccountRequest;
import com.bank.accountservice.model.dto.request.account.PersonalCheckingAccountRequest;
import com.bank.accountservice.model.dto.request.account.PersonalSavingsAccountRequest;
import com.bank.accountservice.model.dto.request.account.PersonalVipSavingsAccountRequest;
import com.bank.accountservice.model.dto.response.AccountResponse;
import com.bank.accountservice.model.dto.response.OperationResponse;
import com.bank.accountservice.model.enums.AccountType;
import com.bank.accountservice.model.enums.ClientType;
import com.bank.accountservice.repository.AccountRepository;
import com.bank.accountservice.util.ResponseMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RequiredArgsConstructor
@Service
public class DefaultAccountService implements AccountService {
    private final AccountRepository repository;
    private final AccountMapper mapper;

    /**
     * Tipos de cuentas
     * 1. Personal - Cuenta de ahorro (Savings Account)
     * 2. Personal - Cuenta corriente (Checking Account)
     * 3. Personal - Cuenta plazo fijo (Fixed Term Account)
     * 4. Personal - Cuenta de ahorro VIP (VIP Savings Account)
     * 5. Empresarial - Cuenta corriente (Checking Account)
     * 6. Empresarial - Cuenta corriente Pyme (Pyme Checking Account)
     */

    @Override
    public Mono<OperationResponse> save(AccountRequest request) {
        return validateRequest(request)
                .then(repository.save(mapper.toDocument(request)))
                .doOnSuccess(document -> log.info("Account {} created successfully", document.getId()))
                .doOnError(error -> log.error("Error creating account: {}", error.getMessage()))
                .doOnTerminate(() -> log.info("Account creation finished"))
                .map(account -> new OperationResponse(ResponseMessage.CREATED_SUCCESSFULLY, HttpStatus.CREATED));
    }

    @Override
    public Flux<AccountResponse> findByClientId(String clientId) {
        return repository.findAccountsByClientId(clientId).map(mapper::toResponse)
                .switchIfEmpty(Mono.error(new NotFoundException(ResponseMessage.NOT_FOUND.getMessage())));
    }

    @Override
    public Mono<AccountResponse> findAccountsByClientIdAndAccountNumber(String clientId, String accountNumber) {
        return repository.findAccountsByClientIdAndAccountNumber(clientId, accountNumber).map(mapper::toResponse)
                .switchIfEmpty(Mono.error(new NotFoundException(ResponseMessage.NOT_FOUND.getMessage())));
    }


    private Mono<Boolean> validateRequest(AccountRequest request) {
        if (request instanceof PersonalSavingsAccountRequest) {
            return canCreatePersonalSavingsAccount(request.getClientId());
        } else if (request instanceof PersonalCheckingAccountRequest) {
            return canCreatePersonalCheckingAccount(request.getClientId());
        } else if (request instanceof PersonalVipSavingsAccountRequest) {
            return canCreatePersonalVipSavingsAccount(request.getClientId());
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
