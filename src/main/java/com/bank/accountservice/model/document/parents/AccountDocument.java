package com.bank.accountservice.model.document.parents;

import com.bank.accountservice.model.enums.BankAccountType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class AccountDocument extends BaseDocument {
    private BankAccountType type;

    private String accountNumber;

    private double balance;

    private boolean isEnabled = true;
}
