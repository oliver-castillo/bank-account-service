package com.bank.accountservice.model.document.transaction;

import com.bank.accountservice.model.document.DestinationAccountApplicable;
import com.bank.accountservice.model.document.Transaction;
import com.bank.accountservice.model.enums.TransactionType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Deposit extends Transaction implements DestinationAccountApplicable {
    private String destinationAccountNumber;

    public Deposit() {
        super(TransactionType.DEPOSIT);
    }

    @Override
    public String getDestinationAccountNumber() {
        return destinationAccountNumber;
    }

    @Override
    public void setDestinationAccountNumber(String recipientAccountNumber) {
        this.destinationAccountNumber = recipientAccountNumber;
    }
}
