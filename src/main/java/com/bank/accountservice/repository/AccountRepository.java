package com.bank.accountservice.repository;

import com.bank.accountservice.model.document.Account;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface AccountRepository extends ReactiveMongoRepository<Account, String> {
    Flux<Account> findAccountsByClientId(String clientId);

    Mono<Account> findAccountsByClientIdAndAccountNumber(String clientId, String accountNumber);

    Mono<Boolean> existsAccountsByAccountNumber(String accountNumber);

    Mono<Account> findAccountByAccountNumber(String accountNumber);
}
