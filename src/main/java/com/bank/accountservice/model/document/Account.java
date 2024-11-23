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

    private final AccountType accountType;

    private final ClientType clientType;

    private final String accountNumber;

    private double balance;

    private int maximumCommissionFreeTransactions;

    protected boolean hasMaintenanceFee() {
        return false;
    }

    protected boolean hasTransactionLimit() {
        return false;
    }

    public boolean requiresCreditCard() {
        return false;
    }

    public boolean requiresAverageMonthlyMinimumAmount() {
        return false;
    }

    protected Account(AccountType accountType, ClientType clientType) {
        this.accountType = accountType;
        this.clientType = clientType;
        this.accountNumber = AccountNumberGenerator.generateAccountNumber().block();
    }
}
