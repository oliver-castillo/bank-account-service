package com.bank.accountservice.model.document;

public interface TransactionLimitApplicable {
    int getTransactionLimit();

    void setTransactionLimit(int transactionLimit);
}
