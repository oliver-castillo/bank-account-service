package com.bank.accountservice.model.document.transaction;

import com.bank.accountservice.model.document.DestinationAccountApplicable;
import com.bank.accountservice.model.document.SourceAccountApplicable;
import com.bank.accountservice.model.document.Transaction;
import com.bank.accountservice.model.enums.TransactionType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Transfer extends Transaction implements SourceAccountApplicable, DestinationAccountApplicable {
    private String sourceAccountNumber;

    private String destinationAccountNumber;

    public Transfer() {
        super(TransactionType.TRANSFER);
    }

    @Override
    public String getDestinationAccountNumber() {
        return destinationAccountNumber;
    }

    @Override
    public void setDestinationAccountNumber(String recipientAccountNumber) {
        this.destinationAccountNumber = recipientAccountNumber;
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
