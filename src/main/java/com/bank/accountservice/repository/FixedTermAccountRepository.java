package com.bank.accountservice.repository;

import com.bank.accountservice.model.document.FixedTermAccountDocument;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FixedTermAccountRepository extends ReactiveMongoRepository<FixedTermAccountDocument, String> {
}
