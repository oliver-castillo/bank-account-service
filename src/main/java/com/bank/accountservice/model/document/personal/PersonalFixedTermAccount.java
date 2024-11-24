package com.bank.accountservice.model.document.personal;

import com.bank.accountservice.exception.BadRequestException;
import com.bank.accountservice.model.document.Account;
import com.bank.accountservice.model.enums.AccountType;
import com.bank.accountservice.model.enums.ClientType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
public class PersonalFixedTermAccount extends Account {
    private int transactionLimit;
    private Set<LocalDate> allowedTransactionDates;

    public PersonalFixedTermAccount() {
        super(AccountType.FIXED_TERM_ACCOUNT, ClientType.PERSONAL);
        this.transactionLimit = 1;
    }

    @Override
    public boolean hasTransactionLimit() {
        return true;
    }

    @Override
    public boolean canMakeWithdrawal(double amount, Long numberOfTransactions) {
        for (LocalDate allowedTransactionDate : allowedTransactionDates) {
            if (allowedTransactionDate.isEqual(LocalDate.now()) && numberOfTransactions < transactionLimit) {
                super.canMakeWithdrawal(amount, numberOfTransactions);
                return true;
            }
        }
        throw new BadRequestException("No puede hacer el retiro");
    }

    @Override
    public boolean canMakeDeposit(double amount, Long numberOfTransactions) {
        for (LocalDate allowedTransactionDate : allowedTransactionDates) {
            if (allowedTransactionDate.isEqual(LocalDate.now()) && numberOfTransactions < transactionLimit) {
                super.canMakeDeposit(amount, numberOfTransactions);
                return true;
            }
        }
        throw new BadRequestException("No puede hacer el depósito");
    }
}
