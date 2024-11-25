package com.bank.accountservice.model.document;

import com.bank.accountservice.model.enums.AccountType;
import com.bank.accountservice.model.enums.ClientType;
import com.bank.accountservice.util.AccountNumberGenerator;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "bank_accounts")
public abstract class Account {
    @Id
    private String id;

    private String clientId;

    private AccountType accountType;

    private ClientType clientType;

    private String accountNumber;

    private double balance;

    private int freeTransactions;

    private double transactionFee;

    private boolean isEnabled = true;

    public boolean hasMaintenanceFee() {
        return false;
    }

    public boolean hasTransactionLimit() {
        return false;
    }

    public boolean requiresCreditCard() {
        return false;
    }

    public boolean requiresAverageMonthlyMinimumAmount() {
        return false;
    }

    private boolean requiresTransactionFee(Long numberOfTransactions) {
        return freeTransactions <= numberOfTransactions;
    }

    public double calculateTransactionFee(Long numberOfTransactions) {
        return requiresTransactionFee(numberOfTransactions) ? transactionFee : 0;
    }

    public boolean canMakeTransfer(double amount, Long numberOfTransactions) {
        double transferAmount = amount - calculateTransactionFee(numberOfTransactions);
        return (balance > 0 && balance >= transferAmount) && isEnabled;
    }

    public boolean canMakeWithdrawal(double amount, Long numberOfTransactions) {
        double withdrawalAmount = amount - calculateTransactionFee(numberOfTransactions);
        return (balance > 0 && balance >= withdrawalAmount) && isEnabled;
    }

    public boolean canMakeDeposit(double amount, Long numberOfTransactions) {
        double depositAmount = amount - calculateTransactionFee(numberOfTransactions);
        return depositAmount > 0 && isEnabled;
    }

    protected Account(AccountType accountType, ClientType clientType) {
        this.accountType = accountType;
        this.clientType = clientType;
        this.accountNumber = AccountNumberGenerator.generateAccountNumber().block();
    }
}
