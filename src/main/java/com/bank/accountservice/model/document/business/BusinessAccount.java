package com.bank.accountservice.model.document.business;

import com.bank.accountservice.model.document.Account;
import com.bank.accountservice.model.enums.AccountType;
import com.bank.accountservice.model.enums.ClientType;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public abstract class BusinessAccount extends Account {
    private Set<Holder> holders;
    private Set<Signatory> signatories;

    protected BusinessAccount(ClientType clientType) {
        super(AccountType.CHECKING_ACCOUNT, clientType);
    }
}
