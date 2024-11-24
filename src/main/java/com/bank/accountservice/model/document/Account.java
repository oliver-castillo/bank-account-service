package com.bank.accountservice.model.document;

import com.bank.accountservice.exception.BadRequestException;
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
        return numberOfTransactions > freeTransactions;
    }

    public double calculateTransactionFee(Long numberOfTransactions) {
        return requiresTransactionFee(numberOfTransactions) ? transactionFee : 0;
    }

    public boolean canMakeTransfer(double amount) {
        return balance > 0 && balance >= amount;
    }

    public boolean canMakeWithdrawal(double amount, Long numberOfTransactions) {
        double withdrawalAmount = amount + calculateTransactionFee(numberOfTransactions);
        if (balance > 0 && balance >= withdrawalAmount) {
            return true;
        } else {
            throw new BadRequestException("La cuenta no tiene suficiente saldo para hacer el retiro");
        }
        //return balance > 0 && balance >= withdrawalAmount;
    }

    public boolean canMakeDeposit(double amount, Long numberOfTransactions) {
        double depositAmount = amount - calculateTransactionFee(numberOfTransactions);
        //return depositAmount > 0;
        if (depositAmount > 0) {
            return true;
        } else {
            throw new BadRequestException("El depósito y la comisión por transacción deben ser mayores a 0");
        }
    }

    protected Account(AccountType accountType, ClientType clientType) {
        this.accountType = accountType;
        this.clientType = clientType;
        this.accountNumber = AccountNumberGenerator.generateAccountNumber().block();
    }
}
