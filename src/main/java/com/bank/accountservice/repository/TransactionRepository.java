package com.bank.accountservice.repository;

import com.bank.accountservice.model.document.Transaction;
import com.bank.accountservice.model.enums.TransactionType;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface TransactionRepository extends ReactiveMongoRepository<Transaction, String> {
    Flux<Transaction> findTransactionsByTransactionType(TransactionType transactionType);
}
