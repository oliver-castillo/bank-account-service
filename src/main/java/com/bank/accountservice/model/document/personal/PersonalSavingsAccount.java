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
    public boolean hasTransactionLimit() {
        return true;
    }

    @Override
    public boolean canMakeWithdrawal(double amount, Long numberOfTransactions) {
        return super.canMakeWithdrawal(amount, numberOfTransactions) && numberOfTransactions <= transactionLimit;
    }

    @Override
    public boolean canMakeDeposit(double amount, Long numberOfTransactions) {
        return super.canMakeDeposit(amount, numberOfTransactions) && numberOfTransactions <= transactionLimit;
    }
}
