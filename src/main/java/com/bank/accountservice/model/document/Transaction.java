package com.bank.accountservice.model.document;

import com.bank.accountservice.model.enums.TransactionType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Setter
@Document(collection = "transactions")
public abstract class Transaction extends BaseDocument {
    private TransactionType transactionType;

    private double amount;

    private LocalDateTime date = LocalDateTime.now();

    private double transactionFee;

    protected Transaction(TransactionType transactionType) {
        this.transactionType = transactionType;
    }
}
