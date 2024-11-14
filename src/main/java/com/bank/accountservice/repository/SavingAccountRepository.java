package com.bank.accountservice.repository;

import com.bank.accountservice.model.document.SavingAccountDocument;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SavingAccountRepository extends ReactiveMongoRepository<SavingAccountDocument, String> {
}
