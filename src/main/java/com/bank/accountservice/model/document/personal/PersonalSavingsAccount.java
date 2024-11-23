package com.bank.accountservice.model.document.personal;

import com.bank.accountservice.model.document.Account;
import com.bank.accountservice.model.document.TransactionLimitApplicable;
import com.bank.accountservice.model.enums.AccountType;
import com.bank.accountservice.model.enums.ClientType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PersonalSavingsAccount extends Account implements TransactionLimitApplicable {
    private int transactionLimit;

    public PersonalSavingsAccount() {
        super(AccountType.SAVINGS_ACCOUNT, ClientType.PERSONAL);
    }

    @Override
    public int getTransactionLimit() {
        return transactionLimit;
    }

    @Override
    public void setTransactionLimit(int transactionLimit) {
        this.transactionLimit = transactionLimit;
    }

    @Override
    protected boolean hasTransactionLimit() {
        return true;
    }
}
