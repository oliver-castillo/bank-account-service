package com.bank.accountservice.repository;

import com.bank.accountservice.model.document.parents.CheckingAccountDocument;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CheckingAccountRepository extends ReactiveMongoRepository<CheckingAccountDocument, String> {
}
