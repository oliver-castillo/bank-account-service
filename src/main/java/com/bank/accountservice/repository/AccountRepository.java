package com.bank.accountservice.repository;

import com.bank.accountservice.model.document.Account;
import com.bank.accountservice.model.enums.AccountType;
import com.bank.accountservice.model.enums.ClientType;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface AccountRepository extends ReactiveMongoRepository<Account, String> {
    Flux<Account> findAccountsByClientId(String clientId);

    Mono<Boolean> existsAccountsByClientIdAndAccountTypeAndClientType(String clientId, AccountType accountType, ClientType clientType);
}
