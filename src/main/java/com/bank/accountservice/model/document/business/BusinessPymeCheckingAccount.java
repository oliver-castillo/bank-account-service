package com.bank.accountservice.model.document.business;

import com.bank.accountservice.model.enums.ClientType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BusinessPymeCheckingAccount extends BusinessAccount {
    public BusinessPymeCheckingAccount() {
        super(ClientType.BUSINESS_PYME);
    }

    @Override
    public boolean requiresCreditCard() {
        return true;
    }
}
