package com.bank.accountservice.model.document.transaction;

import com.bank.accountservice.model.document.SourceAccountApplicable;
import com.bank.accountservice.model.document.Transaction;
import com.bank.accountservice.model.enums.TransactionType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Withdrawal extends Transaction implements SourceAccountApplicable {
    private String sourceAccountNumber;

    public Withdrawal() {
        super(TransactionType.WITHDRAWAL);
    }

    @Override
    public String getSourceAccountNumber() {
        return sourceAccountNumber;
    }

    @Override
    public void setSourceAccountNumber(String senderAccountNumber) {
        this.sourceAccountNumber = senderAccountNumber;
    }
}
